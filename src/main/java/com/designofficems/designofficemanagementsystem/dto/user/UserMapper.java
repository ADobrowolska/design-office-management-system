package com.designofficems.designofficemanagementsystem.dto.user;

import com.designofficems.designofficemanagementsystem.model.Credentials;
import com.designofficems.designofficemanagementsystem.model.User;
import com.designofficems.designofficemanagementsystem.util.AuthenticationRequest;
import com.designofficems.designofficemanagementsystem.util.RegisterRequest;
import com.designofficems.designofficemanagementsystem.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(Role.USER)
                .build();
    }

    public User mapRegisterRequestToUserModel(RegisterRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
    }

    public Credentials mapAuthenticationRequestToCredentialsModel(AuthenticationRequest request) {
        return Credentials.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

}
