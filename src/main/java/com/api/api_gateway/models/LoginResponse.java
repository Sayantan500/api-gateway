package com.api.api_gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse
{
    @JsonProperty("uid")            private String uid;
    @JsonProperty("idToken")        private String accessToken;

    public LoginResponse() {}

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
