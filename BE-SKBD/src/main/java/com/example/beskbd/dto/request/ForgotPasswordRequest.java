package com.example.beskbd.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ForgotPasswordRequest {
    private String email;
}
