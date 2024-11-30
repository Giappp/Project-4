package com.example.beskbd.services;

import com.example.beskbd.dto.object.CategoryDto;
import com.example.beskbd.dto.object.NewArrivalProductDto;
import com.example.beskbd.dto.object.ProductAttributeDto;
import com.example.beskbd.dto.object.ProductSizeDto;
import com.example.beskbd.dto.request.ProductCreationRequest;
import com.example.beskbd.dto.response.ProductDto;
import com.example.beskbd.entities.*;
import com.example.beskbd.exception.AppException;
import com.example.beskbd.exception.ErrorCode;
import com.example.beskbd.repositories.CategoryRepository;
import com.example.beskbd.repositories.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public class ProductService {
    Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final CloudinaryService cloudinaryService;



    public Map<String, List<CategoryDto>> getCategoryByGender() {
        return categoryRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(category -> category.getGender().toString(),
                        Collectors.mapping(CategoryDto::new, Collectors.toList())));
    }

    public ProductDto addProduct(Product product) {
        // Save the product and capture the saved object
        Product savedProduct = productRepository.save(product);

        // Convert the saved product to a ProductDto
        return toProductDto(savedProduct);
    }
    public ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productColors(product.getAttributes().stream()
                        .map(ProductAttribute::getColor) // Collecting colors
                        .collect(Collectors.toList()))
                .productImageUrl(getFirstImageUrl(product)) // Assuming you want the first image URL
                .productMinPrice(getMinPrice(product)) // Assuming you want min and max prices
                .productMaxPrice(getMaxPrice(product))
                .build();
    }


    private ProductAttribute toProductAttribute(ProductAttributeDto dto) {
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setColor(dto.getColor());
        var productImages = dto.getImageFiles()
                .stream()
                .map(imageFile -> {
                    String url;
                    try {
                        url = cloudinaryService.uploadImage(imageFile);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to upload image", e);
                    }
                    var productImage = new ProductImage();
                    productImage.setImageUrl(url);
                    return productImage;
                })
                .toList();
        productAttribute.setProductImages(productImages);
        productAttribute.setSizes(dto
                .getSizes()
                .stream()
                .map(this::toProductSize)
                .toList()
        );
        productAttribute.setPrice(dto.getPrice());
        return productAttribute;
    }

    private ProductSize toProductSize(ProductSizeDto productSizeDto) {
        return ProductSize.builder()
                .size(productSizeDto.getSize())
                .stock(productSizeDto.getStock())
                .build();
    }

    public List<NewArrivalProductDto> getNewArrivalProduct() {
        return productRepository.findTop10ByOrderByCreatedAtDesc()
                .stream()
                .map(this::toNewArrival)
                .toList();
    }

    private NewArrivalProductDto toNewArrival(Product product) {
        return NewArrivalProductDto
                .builder()
                .productId(product.getId())
                .productName(product.getName())
                .minPrice(getMinPrice(product))
                .maxPrice(getMaxPrice(product))
                .imageUrl(getFirstImageUrl(product))
                .build();
    }

    private String getFirstImageUrl(Product product) {
        return product.getAttributes().stream()
                .findFirst()
                .flatMap(attr -> attr.getProductImages().stream().findFirst())
                .map(ProductImage::getImageUrl)
                .orElse(""); // Default to an empty string if no image is available
    }

    private BigDecimal getMaxPrice(Product product) {
        return product.getAttributes()
                .stream()
                .max(Comparator.comparing(ProductAttribute::getPrice))
                .get().getPrice();
    }

    private BigDecimal getMinPrice(Product product) {
        return product.getAttributes()
                .stream()
                .min(Comparator.comparing(ProductAttribute::getPrice))
                .map(ProductAttribute::getPrice)
                .orElse(BigDecimal.ZERO);
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toProductDto)
                .toList();
    }

    public void deleteProductById(Long id) {
            productRepository.deleteById(id);
            System.out.println("Product with ID " + id + " deleted successfully.");

    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public void updateProduct(Long id, ProductCreationRequest request) {
        // Fetch the product by ID, throwing an exception if not found
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        // Update product fields from the request
        product.setName(request.getProductName());  // Use the correct field
        product.setDescription(request.getProductDescription());

        // Fetch the category by ID and set it
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));
        product.setCategory(category);

        // Map the attributes from the request to ProductAttribute entities
        List<ProductAttribute> attributes = request.getAttributes()
                .stream()
                .map(this::toProductAttribute)
                .collect(Collectors.toList());
        product.setAttributes(attributes);

        // Save the updated product back to the repository
        productRepository.save(product);  // Corrected to use productRepository
    }
}
