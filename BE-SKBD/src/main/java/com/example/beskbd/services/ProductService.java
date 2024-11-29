package com.example.beskbd.services;

import com.example.beskbd.dto.object.*;
import com.example.beskbd.dto.request.ProductCreationRequest;
import com.example.beskbd.entities.Product;
import com.example.beskbd.entities.ProductAttribute;
import com.example.beskbd.entities.ProductImage;
import com.example.beskbd.entities.ProductSize;
import com.example.beskbd.exception.AppException;
import com.example.beskbd.exception.ErrorCode;
import com.example.beskbd.repositories.CategoryRepository;
import com.example.beskbd.repositories.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    Logger logger = LoggerFactory.getLogger(ProductService.class);
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    CloudinaryService cloudinaryService;

    public Map<String, List<CategoryDto>> getCategoryByGender() {
        return categoryRepository.findAll()
                .stream()
                .collect(groupingBy(category -> category.getGender().toString(),
                        Collectors.mapping(CategoryDto::new, Collectors.toList())));
    }
    
    @Transactional
    public void addProduct(ProductCreationRequest request) {
        if (request == null) throw new AppException(ErrorCode.INVALID_REQUEST);

        Product product = new Product();
        product.setName(request.getProductName());
        product.setDescription(request.getProductDescription());

        var category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_REQUEST));
        product.setCategory(category);
        if (request.getAttributes() != null) {
            var attributes = request.getAttributes()
                    .stream()
                    .map(this::toProductAttribute)
                    .toList();
            product.setAttributes(attributes);
        }
        productRepository.save(product);
    }

    public Page<Product> searchProducts(ProductFilterDto filter) {
        Pageable pageable = PageRequest.of(
                filter.getPage() != null ? filter.getPage() : 0,
                filter.getPageSize() != null ? filter.getPageSize() : 10
        );
        return productRepository.searchProducts(filter, pageable);
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
                .get().getPrice();
    }

}
