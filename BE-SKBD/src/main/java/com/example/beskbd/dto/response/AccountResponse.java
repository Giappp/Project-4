package com.example.beskbd.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse {
    Boolean activated;
    BigDecimal amountAvailable;
    BigDecimal amountReserved;
    List<String> authorities;
    String email;
    String firstName;
    String lastName;
    String login;
    String imageUrl;
}
