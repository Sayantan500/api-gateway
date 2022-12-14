package com.api.api_gateway.services;

import com.api.api_gateway.models.ResponseObject;
import com.api.api_gateway.models.Solution;
import com.api.api_gateway.models.UpdateSolutionIdFieldOfIssues;
import com.api.api_gateway.models.UpdateStatusFieldOfIssues;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class SolutionServices
{
    private final WebClient webClient;
    private final IssuesServices issuesServices;
    private final String baseUrl;

    public SolutionServices(
            WebClient webClient,
            IssuesServices issuesServices,
            @Qualifier("solutionsBaseUrl") String baseUrl
    ) {
        this.webClient = webClient;
        this.issuesServices = issuesServices;
        this.baseUrl = baseUrl;
    }

    public ResponseObject<Solution> saveNewSolution(String issueId, Solution newSolution)
    {
        AtomicReference<HttpStatus> httpStatus = new AtomicReference<>(HttpStatus.OK);
        Solution solutionResponse =
                webClient
                .post()
                .uri(baseUrl + "/new")
                .bodyValue(newSolution)
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
                .bodyToMono(Solution.class)
                .block();

        if((httpStatus.get()!=HttpStatus.OK)||solutionResponse==null)
            return null;

        Thread threadToUpdateSolutionIdFieldOfIssues = new Thread(() -> {
            UpdateSolutionIdFieldOfIssues updateObj = new UpdateSolutionIdFieldOfIssues();
            updateObj.setNewValue(solutionResponse.get_id());
            issuesServices.updateSolutionIdFieldValueOfIssue(issueId, updateObj);
        });

        Thread threadToUpdateStatusFieldOfIssues = new Thread(()->{
            UpdateStatusFieldOfIssues updateObj = new UpdateStatusFieldOfIssues();
            updateObj.setNewStatus("Solved");
            issuesServices.updateStatusFieldValueOfIssue(issueId, updateObj);
        });

        threadToUpdateStatusFieldOfIssues.start();
        threadToUpdateSolutionIdFieldOfIssues.start();

        try {
            threadToUpdateStatusFieldOfIssues.join(3000);
            threadToUpdateSolutionIdFieldOfIssues.join(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        return new ResponseObject<>(
                solutionResponse,
                httpStatus.get()
        );
    }

    public ResponseObject<Solution> getSolutionFromDatabaseById(String solutionId)
    {
        final String requestUrl = baseUrl + "/" + solutionId;
        AtomicReference<HttpStatus> httpStatus = new AtomicReference<>(HttpStatus.OK);
        Solution solutionResponse =
                webClient
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
                            httpStatus.set(clientResponse.statusCode());
                            return clientResponse.bodyToMono(Throwable.class);
                        })
                        .bodyToMono(Solution.class)
                        .block();

        return new ResponseObject<>(
                solutionResponse,
                httpStatus.get()
        );
    }
}
