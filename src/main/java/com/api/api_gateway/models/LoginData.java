package com.api.api_gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginData
{
    @JsonProperty("username") private final String username;
    @JsonProperty("password") private final String password;

    public LoginData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
