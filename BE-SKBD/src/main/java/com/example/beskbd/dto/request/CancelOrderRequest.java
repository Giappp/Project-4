package com.example.beskbd.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CancelOrderRequest {
    String userName;
    Long orderId;
    Boolean status;
}
