package com.investingsimulator.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public UserActionResponse register(@RequestBody UserRegisterRequest request) {
        String errorMessage = userService.register(request);

        return new UserActionResponse(errorMessage != null, errorMessage);
    }
}
