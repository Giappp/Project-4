package com.example.beskbd.dto.response;

import com.example.beskbd.dto.object.OrderDto;
import com.example.beskbd.dto.object.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDto {
    private UserDto user;
    private OrderDto order;
    private String message;
}
