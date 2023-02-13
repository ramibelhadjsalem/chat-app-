package com.chat.chatConfig;
import com.chat.Dtos.request.MessageDto;
import com.chat.Dtos.response.ChatResponse;
import com.chat.Dtos.response.UserResponse;
import com.chat.Security.jwt.JwtUtils;
import com.chat.chatConfig.validators.MessageValidator;
import com.chat.models.Message;
import com.chat.services.ChatService;
import com.chat.services.UserService;
import com.chat.utils.TokenUtils;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.protocol.Packet;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Component
@Log4j2
public class SocketIOController {
    @Value("${jwtSecret}")
    private String jwtSecret;
    @Autowired
    private SocketIOServer socketServer;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ChatService chatService;
    @Autowired
    private MessageValidator messageValidator;
    public static Map<Long,UserClient> clientMap = new ConcurrentHashMap<>();
    SocketIOController(SocketIOServer socketServer) {
        this.socketServer = socketServer;

        this.socketServer.addConnectListener(onUserConnectWithSocket);
        this.socketServer.addDisconnectListener(onUserDisconnectWithSocket);

        /**
         * Here we create only one event listener
         * but we can create any number of listener
         * messageSendToUser is socket end point after socket connection user have to send message payload on messageSendToUser event
         */
        this.socketServer.addEventListener("messageSendToUser", MessageDto.class, onMessage);
        this.socketServer.addEventListener("sendNotifications", Notification.class, onNotification);

    }


    public ConnectListener onUserConnectWithSocket = new ConnectListener() {
        @Override
        public void onConnect(SocketIOClient client) {

          /*  DefaultHttpHeaders headers = (DefaultHttpHeaders) client.getHandshakeData().getHttpHeaders();
            String jwt = headers.get("Authorization");*/
            String jwt=client.getHandshakeData().getSingleUrlParam("Authorization");
            if (jwt != null && jwt.length() > 0) {
                String token = jwt.substring(7, jwt.length());
                try {
                    Claims claims = Jwts.parser()
                            .setSigningKey(jwtSecret)
                            .parseClaimsJws(token)
                            .getBody();
                    client.getHandshakeData().getUrlParams().put("userId", Collections.singletonList(claims.getId()));
                    UserResponse user = mapper.map(userService.findById(Long.valueOf(claims.getId())),UserResponse.class);
                    log.info(user.getUsername()+" is connect /Perform operation on user connect in controller");
                    socketServer.getBroadcastOperations().sendEvent("userconnect",client,user);
                    clientMap.put(user.getId(),UserClient.builder().client(client).user(user).build());

                    List<UserResponse> userConnected = clientMap.values().stream().map(clientMap-> { return clientMap.getUser();}).collect(Collectors.toList());
                    client.sendEvent("listconnected",userConnected);
                } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
                    client.disconnect();
                }
            } else {
                client.disconnect();
            }


        }
    };


    public DisconnectListener onUserDisconnectWithSocket = new DisconnectListener() {
        @Override
        public void onDisconnect(SocketIOClient client) {
           /* DefaultHttpHeaders headers = (DefaultHttpHeaders) client.getHandshakeData().getHttpHeaders();
            String jwt = headers.get("Authorization");*/

            try {
                String jwt=client.getHandshakeData().getSingleUrlParam("Authorization");
                if (jwt != null && jwt.length() > 0) {
                    if(client.getHandshakeData().getUrlParams()==null) return;
                    Long userId = Long.valueOf(client.getHandshakeData().getUrlParams().get("userId").get(0));
                    UserClient userClient = clientMap.get(userId);
                    if(userClient !=null){

                        log.info(userClient.getUser().getUsername()+" is deconnect  in controller");
                        socketServer.getBroadcastOperations().sendEvent("userdeconnect",client,userClient.getUser());
                        clientMap.remove(userId);
                    }
                }


            }catch(Exception ex){
                log.info("error in logout"+ex.getMessage());
                client.disconnect();
            }
        }
    }
        ;

        public DataListener<MessageDto> onMessage = new DataListener<MessageDto>() {
            @Override
            public void onData(SocketIOClient client, MessageDto message, AckRequest acknowledge) throws Exception {
                try {
                    Long userId = Long.valueOf(client.getHandshakeData().getUrlParams().get("userId").get(0));
                    BindingResult bindingResult = new BeanPropertyBindingResult(message, "message");
                    messageValidator.validate(message, bindingResult);
                    if (bindingResult.hasErrors()) {
                        ObjectMapper mapper = new ObjectMapper();
                        client.sendEvent("validation-error", mapper.writeValueAsString(bindingResult.getAllErrors()));
                        return;
                    }
                    Message saved = chatService.saveMessage(message, userId);
                    UserResponse sender = mapper.map(saved.getSender(),UserResponse.class);
                    ChatResponse m = ChatResponse.builder().id(saved.getId()).content(saved.getContent()).room(saved.getRoom().getId()).sender(sender).build();

                    acknowledge.sendAckData(m);
                    saved.getRoom().getMembers().stream().forEach(member->{
                        UserClient userconnect = clientMap.get(member.getId());
                        if(userconnect !=null && userconnect.getUser().getId() !=userId){
                            userconnect.getClient().sendEvent("chatmessage",m);
                        }
                    });


                } catch (Exception ex) {
                    acknowledge.sendAckData("exception ::   ", ex.getMessage());
                    ex.printStackTrace();
                }
            }
        };

        public DataListener<Notification> onNotification = new DataListener<Notification>() {

            @Override
            public void onData(SocketIOClient client, Notification notification, AckRequest ackRequest) throws Exception {
                log.info("new notification", notification.toString());


                socketServer.getBroadcastOperations().sendEvent("notifications", client, notification);
                ackRequest.sendAckData("notification send succfullyy");
            }
        };



}



