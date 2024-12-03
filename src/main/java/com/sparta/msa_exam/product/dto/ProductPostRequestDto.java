package com.sparta.msa_exam.product.dto;

import com.sparta.msa_exam.product.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPostRequestDto {

    @NotBlank
    private String name;

    @NotNull
    private Integer supplyPrice;

    @NotNull
    private Integer stock;

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .supplyPrice(supplyPrice)
                .stock(stock)
                .build();
    }
}
