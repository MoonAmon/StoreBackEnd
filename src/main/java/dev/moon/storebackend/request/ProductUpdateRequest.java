package dev.moon.storebackend.request;

import lombok.Data;

import java.math.BigDecimal;

import dev.moon.storebackend.model.Category;

@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
