package com.chat.chatConfig;

/*import jakarta.annotation.PreDestroy;*/
import com.chat.Security.jwt.JwtUtils;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.namespace.Namespace;
import io.jsonwebtoken.*;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.PreDestroy;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Component
@Log4j2
public class SocketIOConfig {
    @Value("${jwtSecret}")
    private String jwtSecret;
    @Value("${socket.host}")
    private String SOCKETHOST;
    @Value("${socket.port}")
    private int SOCKETPORT=8878;
    private SocketIOServer server;
    @Autowired private JwtUtils jwtUtils;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(SOCKETHOST);
        config.setPort(SOCKETPORT);

        server = new SocketIOServer(config);
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
               /* DefaultHttpHeaders headers = (DefaultHttpHeaders) client.getHandshakeData().getHttpHeaders();
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
                    } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
                        client.disconnect();
                    }
                } else {
                    client.disconnect();
                }
            }
        });
        server.start();



        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                client.getNamespace().getAllClients().stream().forEach(data-> {
                    log.info("user disconnected "+data.getSessionId().toString());});
            }
        });
        return server;
    }

    @PreDestroy //todo if not work chek import
    public void stopSocketIOServer() {
        this.server.stop();
    }

}



/*SocketIONamespace publicNamespace = server.addNamespace("/public");
        publicNamespace.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                server.getBroadcastOperations().sendEvent("userConnect",client,"user connect"+client.getSessionId().toString());

                server.getBroadcastOperations().sendEvent("userConnect","user connect"+client.getSessionId().toString());

                log.info("new user connected with socket " + client.getSessionId());
            }
        });*/

// todo this will be the public channel with new server

        /*server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                server.getBroadcastOperations().sendEvent("userConnect",client,"user connect"+client.getSessionId().toString());
*//*
                server.getBroadcastOperations().sendEvent("userConnect","user connect"+client.getSessionId().toString());
*//*
                log.info("new user connected with socket " + client.getSessionId());
            }
        });*/ //todo this is base connect