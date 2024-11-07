package com.example.beskbd.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.CompletableFuture;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    CompletableFuture<String> token;
    boolean authenticated;
}
