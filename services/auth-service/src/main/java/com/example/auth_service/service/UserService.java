package com.example.auth_service.service;

import com.example.auth_service.entity.User;
import com.example.auth_service.model.UserRequest;
import com.example.auth_service.model.UserResponse;
import com.example.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest request) {

        User user = new User();
        user.setUsername(request.username());

        String hashedPassword =
                passwordEncoder.encode(request.password());

        user.setPasswordHash(hashedPassword);
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole()
        );
    }
}
