package com.example.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.util.backoff.FixedBackOff;

@SpringBootApplication
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

    @Bean
    JsonMessageConverter converter(){
        return new JsonMessageConverter();
    }

    @Bean
    DefaultErrorHandler errorHandler(KafkaOperations<String,Object> template){
        return new DefaultErrorHandler(new DeadLetterPublishingRecoverer(template),new FixedBackOff(1000L,5));
    }
}
