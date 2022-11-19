package com.api.api_gateway.services;

import com.api.api_gateway.gatewayAPIs.CustomError;
import com.api.api_gateway.models.LoginData;
import com.api.api_gateway.models.LoginResponse;
import com.api.api_gateway.models.ResponseObject;
import com.api.api_gateway.models.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@Service
public class AuthServices
{
    private final WebClient webClient;
    private final String signInBaseUrl;
    private final String signUpBaseUrl;
    private final String verificationBaseUrl;

    public AuthServices(
            WebClient webClient,
            @Qualifier("loginBaseUrl") String signInBaseUrl,
            @Qualifier("signupBaseUrl") String signUpBaseUrl,
            @Qualifier("signupVerificationBaseUrl") String verificationBaseUrl)
    {
        this.webClient = webClient;
        this.signInBaseUrl = signInBaseUrl;
        this.signUpBaseUrl = signUpBaseUrl;
        this.verificationBaseUrl = verificationBaseUrl;
    }

    public ResponseObject<LoginResponse> signIn(LoginData loginData)
    {
        String requestUrl = signInBaseUrl ;
        AtomicReference<HttpStatus> httpStatus = new AtomicReference<>(HttpStatus.OK);
        LoginResponse loginResponse =
                webClient
                        .post()
                        .uri(requestUrl)
                        .bodyValue(loginData)
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                            System.out.println(clientResponse.statusCode());
                            httpStatus.set(HttpStatus.NOT_FOUND);
                            return Mono.error(
                                    new CustomError(
                                            clientResponse.statusCode(),
                                            "User Not Found"
                                    )
                            );
                        })
                        .onStatus(HttpStatus::is5xxServerError,clientResponse -> {
                            System.out.println(clientResponse.statusCode());
                            httpStatus.set(HttpStatus.INTERNAL_SERVER_ERROR);
                            return Mono.error(new CustomError(
                                            clientResponse.statusCode(),
                                            "INTERNAL_SERVER_ERROR"
                                    )
                            );
                        })
                        .bodyToMono(LoginResponse.class)
                        .block();
        return new ResponseObject<>(
                loginResponse,
                httpStatus.get()
        );
    }

    public ResponseEntity<String> signUp(User user)
    {
        final String requestUrl = signUpBaseUrl + "/new";
        return webClient.post()
                .uri(requestUrl)
                .bodyValue(user)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    System.out.println(clientResponse.statusCode());
                    return Mono.error(
                            new CustomError(
                                    clientResponse.statusCode(),
                            "Invalid email address"
                            )
                    );
                })
                .onStatus(HttpStatus::is5xxServerError,clientResponse -> {
                    System.out.println(clientResponse.statusCode());
                    return Mono.error(new CustomError(
                                    clientResponse.statusCode(),
                                    "INTERNAL_SERVER_ERROR"
                            )
                    );
                })
                .toEntity(String.class)
                .block();
    }

    public ResponseEntity<String> verifyOtp(String verificationCode, String sessionId)
    {
        final String requestUrl = verificationBaseUrl + "/verify";

        Consumer<HttpHeaders> httpHeadersConsumer =
                httpHeaders -> httpHeaders.add("verification_code",verificationCode);

        return webClient.post()
                .uri(requestUrl)
                .cookie("session_id",sessionId)
                .headers(httpHeadersConsumer)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    System.out.println(clientResponse.statusCode());
                    return Mono.error(
                            new CustomError(
                                    clientResponse.statusCode(),
                                    "Invalid OTP..."
                            )
                    );
                })
                .onStatus(HttpStatus::is5xxServerError,clientResponse -> {
                    System.out.println(clientResponse.statusCode());
                    return Mono.error(new CustomError(
                            clientResponse.statusCode(),
                            "INTERNAL_SERVER_ERROR"
                        )
                    );
                })
                .toEntity(String.class)
                .block();
    }
}
