package com.sparta.msa_exam.product.service;

import com.sparta.msa_exam.product.dto.ProductResponseDto;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductCacheService {

    @Cacheable(cacheNames = "productCache", key = "#id", unless = "#result == null")
    public ProductResponseDto getCachedProductById(Long id) {
        return null;
    }

    @CachePut(cacheNames = "productCache", key = "#id")
    public ProductResponseDto updateCache(Long id, ProductResponseDto product) {
        return product;
    }
}
