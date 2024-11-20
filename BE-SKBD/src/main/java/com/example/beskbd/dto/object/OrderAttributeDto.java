package com.example.beskbd.dto.object;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderAttributeDto {
    @NotBlank
    private Long orderId;
    @NotBlank
    private Long customerId;
    @NotBlank
    private LocalDateTime orderDate;
    @NotBlank
    private BigDecimal totalAmount;
    @NotBlank
    private String status; // e.g., "Pending", "Completed", "Cancelled"
    @NotBlank
    private String shippingAddress;
}
