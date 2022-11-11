package com.api.api_gateway.services;

import com.api.api_gateway.models.Issues;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class IssuesServices
{
    public static Issues[] getIssuesList(String userID, String lastIssueID)
    {
        System.out.println("User ID : " + userID);

        String baseUrl = "http://localhost:16004/issues?uid="+userID;
        if(lastIssueID!=null)
            baseUrl += "&last-issue-id="+lastIssueID;

        AtomicReference<HttpStatus> httpStatus = new AtomicReference<>(HttpStatus.OK);
        WebClient webClient = WebClient.create();
        Issues[] issues =
                webClient.get()
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
        return httpStatus.get()==HttpStatus.OK ?
                issues :
                null;
    }
}
