package com.api.api_gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse
{
    @JsonProperty("api_key")        private String token;
    @JsonProperty("uid")            private String uid;
    @JsonProperty("accessToken")    private String accessToken;

    public LoginResponse() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
