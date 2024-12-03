package com.sparta.msa_exam.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.IdGeneratorType;

@Entity
@Table(name = "p_products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "supply_price", nullable = false)
    private Integer supplyPrice;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Builder
    private Product(String name, Integer supplyPrice, Integer stock) {
        this.name = name;
        this.supplyPrice = supplyPrice;
        this.stock = stock;
    }

    public void decreaseStock() {
        if(this.stock <= 0) {
            throw new RuntimeException("재고가 부족합니다.");
        }
        this.stock--;
    }

    public void increaseStock() {
        this.stock++;
    }
}
