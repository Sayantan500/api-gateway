package com.api.api_gateway.services;

import com.api.api_gateway.models.ResponseObject;
import com.api.api_gateway.models.UserProfile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.atomic.AtomicReference;
@Service
public class UserServices
{
    private final WebClient webClient;
    private final String baseUrl;

    public UserServices(
            WebClient webClient,
            @Qualifier("usersBaseUrl") String baseUrl
    )
    {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
    }

    public ResponseObject<UserProfile> getUserProfile(String userID)
    {
        AtomicReference<HttpStatus> httpStatus = new AtomicReference<>(HttpStatus.OK);
        WebClient webClient = WebClient.create();
        UserProfile userProfile =
                webClient.get()
                        .uri(baseUrl + "/" + userID + "/profile")
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
                        .bodyToMono(UserProfile.class)
                        .block();
        return new ResponseObject<>(
                userProfile,
                httpStatus.get()
        );
    }
}
