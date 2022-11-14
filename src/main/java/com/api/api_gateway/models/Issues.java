package com.api.api_gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Issues
{
    @JsonProperty("_id")            private String _id;

    @JsonProperty("title")          private String issueTitle;
    @JsonProperty("description")    private String issueDescription;
    @JsonProperty("submitted_by")   private String submittedBy;
    @JsonProperty("to_department")  private String submittedToDepartment;

    @JsonProperty("solution_id")    private String solutionId;

    @JsonProperty("status")         private String status;
    @JsonProperty("created_at")     private long createdAt;

    public Issues() {}

    /**
     * Setters
     * */
    public void set_id(String _id) {
        this._id = _id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public void setSubmittedToDepartment(String submittedToDepartment) {
        this.submittedToDepartment = submittedToDepartment;
    }

    public void setSolutionId(String solutionId) {
        this.solutionId = solutionId;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Getters
     * */
    public String get_id() {
        return _id;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public String getSubmittedToDepartment() {
        return submittedToDepartment;
    }

    public String getSolutionId() {
        return solutionId;
    }

    public String getStatus() {
        return status;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
