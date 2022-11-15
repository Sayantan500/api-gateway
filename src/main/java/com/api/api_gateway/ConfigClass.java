package com.api.api_gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ConfigClass
{
    @Value("${api.baseurl.issues}")
    private String issueBaseUrl;

    @Value("${api.baseurl.users}")
    private String usersBaseUrl;

    @Value("${api.baseurl.solutions}")
    private String solutionsBaseUrl;

    @Value("${api.baseurl.signin}")
    private String signInBaseUrl;

    @Value("${api.baseurl.signup}")
    private String signupBaseUrl;

    @Value("${api.baseurl.signup.verify}")
    private String verificationBaseUrl;

    @Bean
    public WebClient getWebClient()
    {
        return WebClient.create();
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean(name = "issuesBaseUrl")
    public String getIssueBaseUrl(){
        return issueBaseUrl;
    }

    @Bean(name = "usersBaseUrl")
    public String getUsersBaseUrl(){
        return usersBaseUrl;
    }

    @Bean(name = "solutionsBaseUrl")
    public String getSolutionsBaseUrl(){
        return solutionsBaseUrl;
    }

    @Bean(name = "loginBaseUrl")
    public String getSignInBaseUrl(){
        return signInBaseUrl;
    }

    @Bean(name = "signupBaseUrl")
    public String getSignupBaseUrl(){
        return signupBaseUrl;
    }

    @Bean(name = "signupVerificationBaseUrl")
    public String getVerificationBaseUrl(){
        return verificationBaseUrl;
    }
}
