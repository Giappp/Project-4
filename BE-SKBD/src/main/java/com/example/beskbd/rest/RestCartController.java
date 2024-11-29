package com.example.beskbd.rest;

import com.example.beskbd.dto.request.CartDTO;
import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.dto.response.CartItemDTO;
import com.example.beskbd.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*", maxAge = 360000)
public class RestCartController {

    private final CartService cartService;

    @Autowired
    public RestCartController(CartService cartService) {
        this.cartService = cartService;
    }
    @GetMapping("/{cartId}")
    public ResponseEntity<ApiResponse> getBasketById(@PathVariable Long cartId) {
        CartDTO cart = cartService.loadCartById(cartId);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .data(cart)
                .build());
    }

    @GetMapping("/{cartId}/totalPrice")
    public ResponseEntity<ApiResponse> calculateTotalPrice(@PathVariable Long cartId) {
        BigDecimal totalPrice = cartService.calculateTotalPrice(cartId);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .data(totalPrice)
                .build());
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> addCart(@RequestParam String nameCart) {
        CartItemDTO newBasket = cartService.addCart(nameCart);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .data(newBasket)
                .build());
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<ApiResponse> addItemToBasket(@PathVariable Long cartId,
                                                       @RequestParam Long productId,
                                                       @RequestParam int quantity) {
        cartService.addItemToCarts(cartId, productId, quantity);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Item added to basket successfully")
                .build());
    }


    @DeleteMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromBasket(@PathVariable Long cartId)
    {
        cartService.removeItemFromCart(cartId);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Item removed from basket successfully")
                .build());
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse> deleteBasket(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Basket deleted successfully")
                .build());
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<ApiResponse> updateBasket(@PathVariable Long cartId, @RequestParam String nameCart) {
        cartService.updateCart(cartId, nameCart);
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Cart updated successfully")
                .build());
    }
}