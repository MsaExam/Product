package com.sparta.msa_exam.product.controller;

import com.sparta.msa_exam.product.dto.ProductPostRequestDto;
import com.sparta.msa_exam.product.dto.ProductResponseDto;
import com.sparta.msa_exam.product.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponseDto> getProducts(@RequestParam("ids") Set<Long> ids) {
        return productService.getProductsByIds(ids);
    }

    @PostMapping
    public ProductResponseDto createProduct(@Valid @RequestBody ProductPostRequestDto productRequestDto) {
        return productService.createProduct(productRequestDto);
    }

    @PatchMapping("/stocks")
    public List<ProductResponseDto> decreaseProductStocks(@Valid @RequestBody List<Long> requestDtos) {
        return productService.decreaseProductStocks(requestDtos);
    }

    @PatchMapping("/stocks/rollback")
    public List<ProductResponseDto> rollbackProductStocks(@Valid @RequestBody List<Long> requestDtos) {
        return productService.rollbackProductStocks(requestDtos);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException e) {
        log.info("e: ", e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
