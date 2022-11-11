package com.api.api_gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DashboardData
{
    @JsonProperty("profile") private UserProfile userProfile;
    @JsonProperty("issues") private Issues[] issues;

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public void setIssues(Issues[] issues) {
        this.issues = issues;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public Issues[] getIssues() {
        return issues;
    }
}
