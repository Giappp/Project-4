package com.example.beskbd.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RefreshRequest {
    private String refreshToken;
}
