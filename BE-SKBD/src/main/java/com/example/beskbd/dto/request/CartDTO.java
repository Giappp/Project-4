package com.example.beskbd.dto.request;


import com.example.beskbd.dto.response.CartItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CartDTO {
    private List<CartItemDTO> items;

    public CartDTO() {
        this.items = new ArrayList<>();
    }

    public CartDTO(String id, ArrayList<Object> objects) {
        this.items = new ArrayList<>();
        for (Object object : objects) {
            if (object instanceof CartItemDTO) {
                items.add((CartItemDTO) object);
            }
        }

    }
}
