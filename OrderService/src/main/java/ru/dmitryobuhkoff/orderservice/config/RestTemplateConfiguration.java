package ru.dmitryobuhkoff.orderservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.dmitryobuhkoff.orderservice.exceptions.handler.GenerateApiResponseErrorHandler;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfiguration {

    private final GenerateApiResponseErrorHandler generateApiResponseErrorHandler;

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder){
        return  restTemplateBuilder
                .errorHandler(generateApiResponseErrorHandler)
                .setConnectTimeout(Duration.ofSeconds(1))
                .setReadTimeout(Duration.ofSeconds(1))
                .build();
    }

    @Bean
    public RestTemplateBuilder getRestTemplateBuilder(){
        return new RestTemplateBuilder();
    }
}
