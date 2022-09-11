package com.investingsimulator.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(UserRegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            return "User with given username already exists";
        }

        User user = new User(
                request.username(),
                passwordEncoder.encode(request.password())
        );

        userRepository.save(user);

        return null;
    }
}
