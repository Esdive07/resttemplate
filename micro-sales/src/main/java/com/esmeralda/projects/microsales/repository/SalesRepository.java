package com.esmeralda.projects.microsales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.esmeralda.projects.microsales.entity.SalesEntity;

public interface SalesRepository extends JpaRepository<SalesEntity, Integer> {

}
