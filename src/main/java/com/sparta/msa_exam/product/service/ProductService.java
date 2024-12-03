package com.sparta.msa_exam.product.service;

import com.sparta.msa_exam.product.dto.ProductPostRequestDto;
import com.sparta.msa_exam.product.dto.ProductResponseDto;
import com.sparta.msa_exam.product.repository.ProductRepository;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCacheService productCacheService;

    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsByIds(Set<Long> ids) {
        // 1. 캐시에서 데이터 조회
        Map<Long, ProductResponseDto> cachedProducts = ids.stream()
                .map(productCacheService::getCachedProductById)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        ProductResponseDto::getId,  // key
                        Function.identity(),  // value
                        (a, b) -> b  // merge function
                ));

        // 2. 캐시에 없는 ID만 추출
        Set<Long> missingIds = ids.stream()
                .filter(id -> cachedProducts.get(id) == null)
                .collect(Collectors.toSet());

        // 3. 데이터베이스에서 미캐시 ID 조회
        List<ProductResponseDto> missingProducts = productRepository.findAllById(missingIds).stream()
                .map(ProductResponseDto::of)
                .toList();

        // 4. 캐시 업데이트
        missingProducts.forEach(product -> productCacheService.updateCache(product.getId(), product));

        // 5. 최종 결과 병합
        return Stream.concat(cachedProducts.values().stream(), missingProducts.stream())
                .toList();
    }

    @Transactional
    @CachePut(cacheNames = "productCache", key = "#result.id")
    public ProductResponseDto createProduct(ProductPostRequestDto productRequestDto) {
        return ProductResponseDto.of(productRepository.save(productRequestDto.toEntity()));
    }

    @Transactional
    public List<ProductResponseDto> decreaseProductStocks(List<Long> ids) {
        return ids.stream()
                .map(id -> productRepository.findByIdWithPessimisticLock(id)
                        .map(product -> {
                            product.decreaseStock();
                            return productCacheService.updateCache(product.getId(), ProductResponseDto.of(product));
                        })
                        .orElseThrow(()->new RuntimeException("상품이 존재하지 않습니다.")))
                .toList();
    }

    @Transactional
    public List<ProductResponseDto> rollbackProductStocks(List<Long> ids) {
        return ids.stream()
                .map(id -> productRepository.findByIdWithPessimisticLock(id)
                        .map(product -> {
                            product.increaseStock();
                            return productCacheService.updateCache(product.getId(), ProductResponseDto.of(product));
                        })
                        .orElseThrow(()->new RuntimeException("상품이 존재하지 않습니다.")))
                .toList();
    }
}
