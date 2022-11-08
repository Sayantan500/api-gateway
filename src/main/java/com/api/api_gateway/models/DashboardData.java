package com.api.api_gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DashboardData
{
    @JsonProperty("profile") private UserProfile userProfile;
    @JsonProperty("issues") private List<Issues> issues;

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public void setIssues(List<Issues> issues) {
        this.issues = issues;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public List<Issues> getIssues() {
        return issues;
    }
}
