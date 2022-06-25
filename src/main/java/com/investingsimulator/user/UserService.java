package com.investingsimulator.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return "User with given email already exists";
        }

        User user = new User(
                request.getEmail(),
                // TODO hash password
                request.getPassword()
        );

        userRepository.save(user);

        return null;
    }

    public String login(UserLoginRequest request) {
        return null;
    }

    public String logout() {
        return null;
    }
}
