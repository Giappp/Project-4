package com.example.beskbd.config;

import org.springframework.beans.factory.annotation.Value;

//@Configuration
public class KafkaConfig {
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;
}
