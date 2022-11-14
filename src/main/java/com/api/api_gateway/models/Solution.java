package com.api.api_gateway.models;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Solution
{
    @JsonProperty("_id")        private String _id;
    @JsonProperty("solution")   private String solution;
    @JsonProperty("issue_id")   private String providedToIssue;
    @JsonProperty("posted_on")  private long postedOn;

    public Solution(){}

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }

    public void setPostedOn() {
        this.postedOn = new Date().getTime();
    }

    public long getPostedOn() {
        return postedOn;
    }

    public String getSolution() {
        return solution;
    }

    public String getProvidedToIssue() {
        return providedToIssue;
    }
}
