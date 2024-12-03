package com.sparta.msa_exam.product.dto;

import com.sparta.msa_exam.product.entity.Product;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponseDto implements Serializable {
    private final Long id;
    private final String name;
    private final Integer supplyPrice;
    private final Integer stock;

    @Builder
    private ProductResponseDto(Long id, String name, Integer supplyPrice, Integer stock) {
        this.id = id;
        this.name = name;
        this.supplyPrice = supplyPrice;
        this.stock = stock;
    }

    public static ProductResponseDto of(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .supplyPrice(product.getSupplyPrice())
                .stock(product.getStock())
                .build();
    }
}
