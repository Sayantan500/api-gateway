package com.api.api_gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserProfile
{
    @JsonProperty("name")
    private String Name;

    @JsonProperty("roll_no")
    private int Roll_Number;

    @JsonProperty("registration_no")
    private long Registration_Number;

    @JsonProperty("email")
    private String Email;

    public UserProfile() {}
}
