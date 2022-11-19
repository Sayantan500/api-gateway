package com.api.api_gateway.gatewayAPIs;

import com.api.api_gateway.models.*;
import com.api.api_gateway.services.IssuesServices;
import com.api.api_gateway.services.SolutionServices;
import com.api.api_gateway.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class Dashboard
{
    private final IssuesServices issuesServices;
    private final UserServices userServices;
    private final SolutionServices solutionServices;

    public Dashboard(IssuesServices issuesServices, UserServices userServices, SolutionServices solutionServices) {
        this.issuesServices = issuesServices;
        this.userServices = userServices;
        this.solutionServices = solutionServices;
    }

    @GetMapping("/")
    public ResponseEntity<DashboardData> getInitialDashboardData(
            @RequestHeader(name = "uid") String userID
    )
    {
        HttpStatus status = HttpStatus.OK;

        ResponseObject<UserProfile> userProfile = userServices.getUserProfile(userID); //getting the user profile data
        ResponseObject<Issues[]> issues = issuesServices.getIssuesList(userID,null); //getting the issues of the user

        DashboardData dashboardData;

        if(userProfile.getStatus()!=HttpStatus.OK || issues.getStatus()!=HttpStatus.OK)
        {
            dashboardData = null;
            status = HttpStatus.NOT_FOUND;
        }
        else
        {
            dashboardData = new DashboardData();
            dashboardData.setUserProfile(userProfile.getMessageBody());
            dashboardData.setIssues(issues.getMessageBody());
        }

        return new ResponseEntity<>(dashboardData, status);
    }

    @PostMapping("/compose/new")
    public ResponseEntity<Object> getNewPostFromUserAndSave(@RequestBody Issues newPost)
    {
        ResponseObject<Issues> response = issuesServices.saveNewPost(newPost);
        return new ResponseEntity<>(
                response.getMessageBody(),
                response.getStatus()
        );
    }

    @GetMapping("/issues")
    public ResponseEntity<Object> getAllIssuesOfAnUser(
            @RequestParam(name = "uid") String uid,
            @RequestParam(name = "last-issue-id",required = false) String lastIssue
    )
    {
        ResponseObject<Issues[]> response = issuesServices.getIssuesList(uid, lastIssue);
        return new ResponseEntity<>(
                response.getMessageBody(),
                response.getStatus()
        );
    }

    @PostMapping("/solution/new")
    public ResponseEntity<Object> sendNewSolutionForIssue(@RequestBody Solution newSolution)
    {
        ResponseObject<Solution> solutionResponseObject =
                solutionServices.saveNewSolution(
                        newSolution.getProvidedToIssue(),
                        newSolution
                );
        return new ResponseEntity<>(
                solutionResponseObject.getMessageBody(),
                solutionResponseObject.getStatus()
        );
    }
}
