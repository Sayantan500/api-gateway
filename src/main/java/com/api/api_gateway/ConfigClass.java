package com.api.api_gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ConfigClass
{
    @Bean
    public WebClient getWebClient()
    {
        return WebClient.create();
    }
}
