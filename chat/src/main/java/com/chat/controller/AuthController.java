package com.chat.controller;

import com.chat.Dtos.request.LoginForm;
import com.chat.Dtos.request.SignUpDto;
import com.chat.Dtos.response.JwtResponse;
import com.chat.Dtos.response.MessageResponse;
import com.chat.Security.jwt.JwtUtils;
import com.chat.Security.userService.UserDetailServiceImpl;
import com.chat.Security.userService.UserDetailsImpl;
import com.chat.models.ERole;
import com.chat.models.Role;
import com.chat.models.User;
import com.chat.services.RoleService;
import com.chat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private UserService userService;
    @Autowired private RoleService roleService;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private PasswordEncoder encoder;
    @Autowired private JwtUtils jwtUtils;
    @Value("${jwtSecret}") private String jwtSecret;

    @Value("${jwtExpirationMs}") private int jwtExpirationMs;
    @Autowired private UserDetailServiceImpl userDetailService;
    @PostMapping("signup")
    public ResponseEntity addUser(@Valid @RequestBody SignUpDto signUpDto){
        if (userService.existsByUsername(signUpDto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        User user = User.builder()
                        .username(signUpDto.getUsername())
                        .email(signUpDto.getEmail())
                        .password(encoder.encode(signUpDto.getPassword()))
                        .build();

        Role userRole = roleService.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRoles(Set.of(userRole));

        userService.saveUser(user);
        return new ResponseEntity<>(new MessageResponse("User registered successfully!"), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginForm) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        String refreshJwt = jwtUtils.generateRefreshJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        return ResponseEntity.ok(new JwtResponse(
                jwt,
                refreshJwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles,
                userDetails.getUserimg()));
    }
}
