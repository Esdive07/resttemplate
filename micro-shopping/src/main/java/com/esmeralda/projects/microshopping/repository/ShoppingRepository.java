package com.esmeralda.projects.microshopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.esmeralda.projects.microshopping.entity.ShoppingEntity;

public interface ShoppingRepository extends JpaRepository<ShoppingEntity, Integer> {

}
