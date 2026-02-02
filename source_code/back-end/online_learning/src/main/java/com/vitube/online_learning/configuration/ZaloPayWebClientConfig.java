package com.vitube.online_learning.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ZaloPayWebClientConfig {

    @Value("${zalopay.baseUrl}")
    private String ZALOPAY_BASE_URL;

    @Bean
    public WebClient zaloPayWebClient() {
        return WebClient.builder()
                .baseUrl(ZALOPAY_BASE_URL) // sandbox
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}