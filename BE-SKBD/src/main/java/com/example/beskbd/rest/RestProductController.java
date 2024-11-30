package com.example.beskbd.rest;

import com.example.beskbd.dto.object.ProductFilterDto;
import com.example.beskbd.dto.request.ProductCreationRequest;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.entities.Product;
import com.example.beskbd.services.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RestProductController {
    static Logger logger = LoggerFactory.getLogger(RestProductController.class);
    @Autowired
    ProductService productService;

    @GetMapping("/genders")
    public ResponseEntity<?> getByGender() {
        var result = productService.getCategoryByGender();
        return ResponseEntity.ok(ApiResponse.builder()
                .data(result)
                .success(true)
                .build());
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@ModelAttribute ProductCreationRequest request) {
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }
        logger.info(request.toString());
        logger.info("Create new Product");
        productService.addProduct(request);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .build());
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestBody(required = false) ProductFilterDto filter) {
        Page<Product> products = productService.searchProducts(filter);
        return ResponseEntity.ok(ApiResponse.builder()
                .data(products)
                .success(true)
        );
    }

    @GetMapping("/new-arrivals")
    public ResponseEntity<?> getNewArrivals() {
        var products = productService.getNewArrivalProduct();
        return ResponseEntity.ok(ApiResponse.builder()
                .data(products)
                .success(true)
                .build());
    }
}