package org.example.dianping.config;

import org.apache.pulsar.client.api.PulsarClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class PulsarConfig {

    @Value("${spring.pulsar.client.serviceUrl}")
    private String serviceUrl;

    @Bean
    public PulsarClient pulsarClient() throws Exception {
        return PulsarClient.builder()
                .serviceUrl(serviceUrl)
                .build();
    }
}