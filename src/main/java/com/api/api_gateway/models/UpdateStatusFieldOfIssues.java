package com.api.api_gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateStatusFieldOfIssues
{
    @JsonProperty("new_status") private String newStatus;

    public UpdateStatusFieldOfIssues() {}

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }
}
