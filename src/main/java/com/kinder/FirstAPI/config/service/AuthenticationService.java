package com.kinder.FirstAPI.config.service;

import com.kinder.FirstAPI.controller.auth.AuthenticationRequest;
import com.kinder.FirstAPI.controller.auth.AuthenticationResponse;
import com.kinder.FirstAPI.controller.auth.RegisterRequest;
import com.kinder.FirstAPI.exception.UserNotFoundException;
import com.kinder.FirstAPI.model.Role;
import com.kinder.FirstAPI.model.User;
import com.kinder.FirstAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        this.repository.save(user);
        String jwtToken = this.jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        UsernamePasswordAuthenticationToken tempAuthToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        this.authenticationManager.authenticate(tempAuthToken);

        User user = this.repository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        String jwtToken = this.jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
