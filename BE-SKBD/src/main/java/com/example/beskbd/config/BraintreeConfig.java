package com.example.beskbd.config;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BraintreeConfig {
    @Bean
    public BraintreeGateway braintreeGateway() {
        return new BraintreeGateway(
                Environment.SANDBOX, // Use Environment.Production for live
                "529rsk5hb8qqzfrf",
                "9ng443b593dc84cq",
                "4208b67a9fd3fb5bfe75ed8d8d05d6eb"
        );
    }
}
