package com.kshrd.demo_openfeign.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String title;
    private String description;
    private Double price;
    private Integer stock;
    private String brand;
    private String category;
}