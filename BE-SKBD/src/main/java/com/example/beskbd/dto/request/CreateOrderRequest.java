package com.example.beskbd.dto.request;

import com.example.beskbd.dto.events.CartItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderRequest {
    @NotNull
    String username;
    @NotBlank
    String address;
    @Size(min = 1, max = 255)
    List<CartItem> items;
}
