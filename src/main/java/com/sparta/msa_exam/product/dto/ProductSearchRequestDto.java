package com.sparta.msa_exam.product.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductSearchRequestDto {
    private List<Long> ids;
    private String name;
    private Integer minPrice;
    private Integer maxPrice;
}
