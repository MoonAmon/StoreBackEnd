package dev.moon.storebackend.request;

import dev.moon.storebackend.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private int inventory;
    private BigDecimal price;
    private String description;
    private Category category;
}
