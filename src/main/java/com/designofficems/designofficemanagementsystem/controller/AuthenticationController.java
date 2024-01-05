package com.designofficems.designofficemanagementsystem.controller;

import com.designofficems.designofficemanagementsystem.dto.user.UserMapper;
import com.designofficems.designofficemanagementsystem.service.AuthenticationService;
import com.designofficems.designofficemanagementsystem.util.AuthenticationRequest;
import com.designofficems.designofficemanagementsystem.util.AuthenticationResponse;
import com.designofficems.designofficemanagementsystem.util.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, UserMapper userMapper) {
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request)
            throws InstanceAlreadyExistsException {
        return ResponseEntity.ok(authenticationService.register(userMapper.mapRegisterRequestToUserModel(request)));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(userMapper.mapAuthenticationRequestToCredenrialsModel(request)));
    }

}
