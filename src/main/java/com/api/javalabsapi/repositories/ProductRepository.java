package com.api.javalabsapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.javalabsapi.model.ProductModel;

@Repository //Não precisaria porque o JpaRepository ja tem é implicito
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {
    
}
