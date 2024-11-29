package com.example.beskbd;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BeSkbdApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeSkbdApplication.class, args);
    }

    @Bean
    NewTopic orderProcess() {
        return new NewTopic("inventory-orders", 2, (short) 1);
    }

    @Bean
    NewTopic paymentProcess() {
        return new NewTopic("payment", 2, (short) 1);
    }

    @Bean
    NewTopic notifications() {
        return new NewTopic("order-notifications", 2, (short) 1);
    }

}
