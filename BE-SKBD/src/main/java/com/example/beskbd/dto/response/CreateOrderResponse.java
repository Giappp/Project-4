package com.example.beskbd.dto.response;

import com.example.beskbd.dto.object.OrderDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderResponse {
    OrderDto order;
}
