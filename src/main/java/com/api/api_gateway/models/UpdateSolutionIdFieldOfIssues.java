package com.api.api_gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateSolutionIdFieldOfIssues
{
    @JsonProperty("new_solution_id")    private String newValue;

    public UpdateSolutionIdFieldOfIssues() {}

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
