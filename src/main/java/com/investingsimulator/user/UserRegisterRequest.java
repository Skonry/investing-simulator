package com.investingsimulator.user;

public record UserRegisterRequest (
        String username,
        String password
) {
}
