package com.investingsimulator.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public UserActionResponse register(@RequestBody UserRegisterRequest request) {
        String errorMessage = userService.register(request);

        return new UserActionResponse(errorMessage != null, errorMessage);
    }

    @PostMapping(value = "/login")
    public UserActionResponse login(@RequestBody UserLoginRequest request) {
        String errorMessage = userService.login(request);

        return new UserActionResponse(errorMessage != null, errorMessage);
    }

    @PostMapping(value = "/logout")
    public UserActionResponse logout() {
        String errorMessage = userService.logout();

        return new UserActionResponse(errorMessage != null, errorMessage);
    }
}
