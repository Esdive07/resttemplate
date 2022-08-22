package com.rah.demo.microproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rah.demo.microproduct.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

}
