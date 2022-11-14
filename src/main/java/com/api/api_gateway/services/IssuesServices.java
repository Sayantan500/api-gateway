package com.api.api_gateway.services;

import com.api.api_gateway.models.Issues;
import com.api.api_gateway.models.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.atomic.AtomicReference;
@Service
public class IssuesServices
{
    private final WebClient webClient;

    public IssuesServices(WebClient webClient) {
        this.webClient = webClient;
    }

    public ResponseObject<Issues[]> getIssuesList(String userID, String lastIssueID)
    {
        System.out.println("User ID : " + userID);

        String baseUrl = "http://localhost:16004/issues?uid="+userID;
        if(lastIssueID!=null)
            baseUrl += "&last-issue-id="+lastIssueID;

        AtomicReference<HttpStatus> httpStatus = new AtomicReference<>(HttpStatus.OK);
        Issues[] issues =
                webClient
                        .get()
                        .uri(baseUrl)
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError,clientResponse -> {
                            System.out.println(clientResponse.statusCode());
                            httpStatus.set(HttpStatus.NOT_FOUND);
                            return clientResponse.bodyToMono(Throwable.class);
                        })
                        .onStatus(HttpStatus::is5xxServerError,clientResponse -> {
                            System.out.println(clientResponse.statusCode());
                            httpStatus.set(HttpStatus.INTERNAL_SERVER_ERROR);
                            return clientResponse.bodyToMono(Throwable.class);
                        })
                        .bodyToMono(Issues[].class)
                        .block();
        return new ResponseObject<>(
                issues,
                httpStatus.get()
        );
    }

    public ResponseObject<Issues> saveNewPost(Issues newIssues)
    {
        AtomicReference<HttpStatus> httpStatus = new AtomicReference<>(HttpStatus.OK);
        Issues response =
                webClient
                        .post()
                        .uri("http://localhost:16004/issues/new")
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
}
