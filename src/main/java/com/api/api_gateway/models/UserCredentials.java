package com.api.api_gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCredentials
{
    @JsonProperty("email")  private String email;
    @JsonProperty("passphrase")  private String password;

    public UserCredentials() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
