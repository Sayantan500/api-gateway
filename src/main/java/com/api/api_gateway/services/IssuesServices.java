package com.api.api_gateway.services;

import com.api.api_gateway.models.Issues;
import com.api.api_gateway.models.ResponseObject;
import com.api.api_gateway.models.UpdateSolutionIdFieldOfIssues;
import com.api.api_gateway.models.UpdateStatusFieldOfIssues;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.atomic.AtomicReference;
@Service
public class IssuesServices
{
    private final WebClient webClient;
    private final String baseUrl;

    public IssuesServices(
            WebClient webClient,
            @Qualifier("issuesBaseUrl") String baseUrl
    )
    {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
    }

    public ResponseObject<Issues[]> getIssuesList(String userID, String lastIssueID)
    {
        String requestUrl = baseUrl + "?" + "uid="+userID;
        if(lastIssueID!=null)
            requestUrl += "&last-issue-id="+lastIssueID;
        AtomicReference<HttpStatus> httpStatus = new AtomicReference<>(HttpStatus.OK);
        Issues[] issues = fetchIssuesHistory(httpStatus,requestUrl);
        return new ResponseObject<>(
                issues,
                httpStatus.get()
        );
    }

    public ResponseObject<Issues[]> getIssuesListByDepartment(String deptName, String lastIssueID)
    {
        String requestUrl = baseUrl + "?" + "dept="+deptName;
        if(lastIssueID!=null)
            requestUrl += "&last-issue-id="+lastIssueID;
        AtomicReference<HttpStatus> httpStatus = new AtomicReference<>(HttpStatus.OK);
        Issues[] issues = fetchIssuesHistory(httpStatus,requestUrl);
        return new ResponseObject<>(
                issues,
                httpStatus.get()
        );
    }

    private Issues[] fetchIssuesHistory(AtomicReference<HttpStatus> httpStatus, String requestUrl)
    {
        return webClient
                .get()
                .uri(requestUrl)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,clientResponse -> {
                    System.out.println(clientResponse.statusCode());
                    httpStatus.set(clientResponse.statusCode());
                    return clientResponse.bodyToMono(Throwable.class);
                })
                .onStatus(HttpStatus::is5xxServerError,clientResponse -> {
                    System.out.println(clientResponse.statusCode());
                    httpStatus.set(HttpStatus.INTERNAL_SERVER_ERROR);
                    return clientResponse.bodyToMono(Throwable.class);
                })
                .bodyToMono(Issues[].class)
                .block();
    }

    public ResponseObject<Issues> saveNewPost(Issues newIssues)
    {
        AtomicReference<HttpStatus> httpStatus = new AtomicReference<>(HttpStatus.OK);
        Issues response =
                webClient
                        .post()
                        .uri(baseUrl + "/new")
                        .bodyValue(newIssues)
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError,clientResponse -> {
                            System.out.println(clientResponse.statusCode());
                            httpStatus.set(clientResponse.statusCode());
                            return clientResponse.bodyToMono(Throwable.class);
                        })
                        .onStatus(HttpStatus::is5xxServerError,clientResponse -> {
                            System.out.println(clientResponse.statusCode());
                            httpStatus.set(clientResponse.statusCode());
                            return clientResponse.bodyToMono(Throwable.class);
                        })
                        .bodyToMono(Issues.class)
                        .block();

        return new ResponseObject<>(
                response,
                httpStatus.get()
        );
    }

    void updateSolutionIdFieldValueOfIssue(String issueId, UpdateSolutionIdFieldOfIssues updateObj)
    {
        webClient
                .patch()
                .uri(baseUrl + "/" + issueId + "/solution-id")
                .bodyValue(updateObj)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,clientResponse -> {
                    System.out.println(clientResponse.statusCode());
                    return clientResponse.bodyToMono(Throwable.class);
                })
                .onStatus(HttpStatus::is5xxServerError,clientResponse -> {
                    System.out.println(clientResponse.statusCode());
                    return clientResponse.bodyToMono(Throwable.class);
                })
                .toBodilessEntity()
                .block();
    }

    void updateStatusFieldValueOfIssue(String issueId, UpdateStatusFieldOfIssues updateObj)
    {
        webClient
                .patch()
                .uri(baseUrl + "/" + issueId + "/status")
                .bodyValue(updateObj)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,clientResponse -> {
                    System.out.println(clientResponse.statusCode());
                    return clientResponse.bodyToMono(Throwable.class);
                })
                .onStatus(HttpStatus::is5xxServerError,clientResponse -> {
                    System.out.println(clientResponse.statusCode());
                    return clientResponse.bodyToMono(Throwable.class);
                })
                .toBodilessEntity()
                .block();
    }
}
