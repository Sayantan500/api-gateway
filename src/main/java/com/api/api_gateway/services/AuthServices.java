package com.api.api_gateway.services;

import com.api.api_gateway.models.LoginData;
import com.api.api_gateway.models.LoginResponse;
import com.api.api_gateway.models.ResponseObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class AuthServices
{
    private final WebClient webClient;
    private final String baseUrl;

    public AuthServices(
            WebClient webClient,
            @Qualifier("loginBaseUrl") String baseUrl
    )
    {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
    }

    public ResponseObject<LoginResponse> signIn(LoginData loginData)
    {
        String requestUrl = baseUrl +"?u=" + loginData.getUsername() + "&p=" + loginData.getPassword();
        AtomicReference<HttpStatus> httpStatus = new AtomicReference<>(HttpStatus.OK);
        LoginResponse loginResponse =
                webClient
                        .post()
                        .uri(requestUrl)
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                            System.out.println(clientResponse.statusCode());
                            httpStatus.set(HttpStatus.NOT_FOUND);
                            return clientResponse.bodyToMono(Throwable.class);
                        })
                        .onStatus(HttpStatus::is5xxServerError,clientResponse -> {
                            System.out.println(clientResponse.statusCode());
                            httpStatus.set(HttpStatus.INTERNAL_SERVER_ERROR);
                            return clientResponse.bodyToMono(Throwable.class);
                        })
                        .bodyToMono(LoginResponse.class)
                        .block();
        return new ResponseObject<>(
                loginResponse,
                httpStatus.get()
        );
    }
}
