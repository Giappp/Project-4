package com.example.beskbd.config;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        Dotenv dotenv = Dotenv.load();

        String cloudinaryUrl = dotenv.get("CLOUDINARY_URL");
        if (cloudinaryUrl == null) {
            throw new RuntimeException("CLOUDINARY_URL is not set in the environment variables.");
        }

        System.out.println("Cloudinary URL: " + cloudinaryUrl); // Debugging line

        return new Cloudinary(cloudinaryUrl);
    }
}