package com.example.beskbd.services;

import com.example.beskbd.dto.request.CartDTO;
import com.example.beskbd.dto.response.CartItemDTO;
import com.example.beskbd.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.*;

@Getter
@Service
@Setter
public class ShoppingCartService {
    private final List<CartItemDTO> cartItems = new ArrayList<>();

    // Add an item to the cart
    public void addItem(CartItemDTO cartItemDto) {
        // Check if the item already exists in the cart
        for (CartItemDTO item : cartItems) {
            if (item.getProductId().equals(cartItemDto.getProductId())) {
                // If it exists, update the quantity
                item.setQuantity(item.getQuantity() + cartItemDto.getQuantity());
                return;
            }
        }
        // If it doesn't exist, add it to the cart
        cartItems.add(cartItemDto);
    }

    // Remove an item from the cart
    public void removeItem(Long itemId) {
        cartItems.removeIf(item -> item.getProductId().equals(itemId));
    }

    // View all items in the cart
    public List<CartItemDTO> viewCart() {
        return new ArrayList<>(cartItems);
    }

    // Calculate total price of the cart
    public double calculateTotal() {
        return cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    // Clear the cart
    public void clearCart() {
        cartItems.clear();
    }
}