package com.api.api_gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User
{
    @JsonProperty("biodata")    private UserProfile userProfile;
    @JsonProperty("credentials")    private UserCredentials userCredentials;

    public User() {}

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
    }
}
