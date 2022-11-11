package com.api.api_gateway.gatewayAPIs;

import com.api.api_gateway.models.DashboardData;
import com.api.api_gateway.models.Issues;
import com.api.api_gateway.models.UserProfile;
import com.api.api_gateway.services.IssuesServices;
import com.api.api_gateway.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class Dashboard
{
    @GetMapping("/")
    public ResponseEntity<DashboardData> getInitialDashboardData(
            @RequestHeader(name = "uid") String userID
    )
    {
        HttpStatus status = HttpStatus.OK;

        UserProfile userProfile = UserServices.getUserProfile(userID); //getting the user profile data
        Issues[] issues = IssuesServices.getIssuesList(userID,null); //getting the issues of the user

        DashboardData dashboardData;

        if(userProfile==null || issues==null)
        {
            dashboardData = null;
            status = HttpStatus.NOT_FOUND;
        }
        else
        {
            dashboardData = new DashboardData();
            dashboardData.setUserProfile(userProfile);
            dashboardData.setIssues(issues);
        }

        return new ResponseEntity<>(dashboardData, status);
    }
}
