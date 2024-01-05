package com.designofficems.designofficemanagementsystem.service;

import com.designofficems.designofficemanagementsystem.model.Credentials;
import com.designofficems.designofficemanagementsystem.model.User;
import com.designofficems.designofficemanagementsystem.repository.UserRepository;
import com.designofficems.designofficemanagementsystem.util.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User user) throws InstanceAlreadyExistsException {
        if (!userRepository.existsByEmail(user.getEmail())) {
            if (validateEmail(user)) {
                userRepository.save(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
            } else {
                throw new IllegalArgumentException("Invalid email address");
            }
        } else {
            throw new InstanceAlreadyExistsException("User exists");
        }
    }

    public AuthenticationResponse authenticate(Credentials credentials) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())
        );
        User foundUser = userRepository.findByEmail(credentials.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(foundUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private boolean validateEmail(User user) {
        String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(user.getEmail());
        return matcher.matches();
    }
}
