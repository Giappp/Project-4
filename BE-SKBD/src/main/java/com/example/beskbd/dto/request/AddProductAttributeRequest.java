package com.example.beskbd.dto.request;

import com.example.beskbd.dto.object.ProductSizeDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddProductAttributeRequest {
    @NotEmpty
    private String color;
    @NotNull
    private BigDecimal price;
    @Size(min = 1)
    private List<ProductSizeDto> sizes;
    @Size(min = 1)
    private List<MultipartFile> images;
}
