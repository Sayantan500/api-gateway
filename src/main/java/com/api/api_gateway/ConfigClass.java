package com.api.api_gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    @Bean
    public WebClient getWebClient()
    {
        return WebClient.create();
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
}
