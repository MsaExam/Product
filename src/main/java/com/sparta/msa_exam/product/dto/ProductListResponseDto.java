package com.sparta.msa_exam.product.dto;

import com.sparta.msa_exam.product.entity.Product;
import java.util.List;
import lombok.Builder;

public class ProductListResponseDto {

    private List<ProductResponseDto> products;

    @Builder
    private ProductListResponseDto(List<ProductResponseDto> products) {
        this.products = products;
    }

    public static ProductListResponseDto of (List<Product> products) {
        return ProductListResponseDto.builder()
                .products(products.stream()
                        .map(ProductResponseDto::of)
                        .toList())
                .build();
    }
}
