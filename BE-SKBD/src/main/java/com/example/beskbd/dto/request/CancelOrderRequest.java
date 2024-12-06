package com.example.beskbd.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CancelOrderRequest {
    String userName;
    @NotNull
    Long orderId;
    Boolean status;
}
