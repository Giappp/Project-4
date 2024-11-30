package com.example.beskbd.rest;

import com.example.beskbd.dto.object.CategoryDto;
import com.example.beskbd.dto.object.NewArrivalProductDto;
import com.example.beskbd.dto.request.ProductCreationRequest;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.dto.response.ProductDto;
import com.example.beskbd.entities.Product;
import com.example.beskbd.exception.AppException;
import com.example.beskbd.exception.ErrorCode;
import com.example.beskbd.services.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
@CrossOrigin(origins = "*", maxAge = 360000)
public class RestProductController {
    static Logger logger = LoggerFactory.getLogger(RestProductController.class);

    @GetMapping("/by-gender")
    public ResponseEntity<ApiResponse<Map<String, List<CategoryDto>>>> getByGender() {
        // Call the service method to get categories by gender
        Map<String, List<CategoryDto>> categories = productService.getCategoryByGender();

        // Build and return the response with the categories
        return ResponseEntity.ok(ApiResponse.<Map<String, List<CategoryDto>>>builder()
                .success(true)
                .data(categories) // Include the categories in the response
                .build());
    }
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@ModelAttribute Product request) {
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }
        logger.info(request.toString());
        productService.addProduct(request);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .success(true)
                .data(product)
                .build());
    }


    private final ProductService productService;
    public RestProductController(ProductService productService){
        this.productService = productService;
    }// Constructor-based injection

    @GetMapping("/new-arrivals")
    public ResponseEntity<?> getNewArrivals() {
        List<NewArrivalProductDto> newArrivals = productService.getNewArrivalProduct();
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .data(newArrivals)
                .build());
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .data(products)
                .build());
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .build());
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductCreationRequest request) {
        productService.updateProduct(id, request);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .build());
    }

}