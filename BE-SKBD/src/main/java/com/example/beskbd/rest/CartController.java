package com.example.beskbd.rest;

import com.example.beskbd.dto.response.ApiResponse;

import com.example.beskbd.dto.response.CartItemDTO;
import com.example.beskbd.services.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private ShoppingCartService cartService;

    @PostMapping("/add")
    public ApiResponse<String> addItemToCart(@RequestBody CartItemDTO cartItemDto) {
        logger.info("Adding item to cart: {}", cartItemDto);
        cartService.addItem(cartItemDto);
        return ApiResponse.<String>builder()
                .data("Item added successfully")
                .message("Item added to cart")
                .success(true)
                .build();
    }

    @DeleteMapping("/remove/{itemId}")
    public ApiResponse<String> removeItemFromCart(@PathVariable Long itemId) {
        logger.info("Removing item from cart: {}", itemId);
        cartService.removeItem(itemId);
        return ApiResponse.<String>builder()
                .data("Item removed successfully")
                .message("Item removed from cart")
                .success(true)
                .build();
    }

    @GetMapping("/view")
    public ApiResponse<List<CartItemDTO>> viewCart() {
        logger.info("Viewing cart contents");
        List<CartItemDTO> cartItems = cartService.viewCart();
        return ApiResponse.<List<CartItemDTO>>builder()
                .data(cartItems)
                .message("Cart retrieved successfully")
                .success(true)
                .build();
    }

    @GetMapping("/total")
    public ApiResponse<Double> calculateTotal() {
        logger.info("Calculating total price of cart");
        double total = cartService.calculateTotal();
        return ApiResponse.<Double>builder()
                .data(total)
                .message("Total price calculated successfully")
                .success(true)
                .build();
    }

    @DeleteMapping("/clear")
    public ApiResponse<String> clearCart() {
        logger.info("Clearing cart");
        cartService.clearCart();
        return ApiResponse.<String>builder()
                .data("Cart cleared successfully")
                .message("Cart cleared")
                .success(true)
                .build();
    }

}
