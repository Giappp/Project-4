package com.example.beskbd.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilterOptionResponse {
    List<Integer> sizes;
    List<String> colors;
    List<String> types;
}
