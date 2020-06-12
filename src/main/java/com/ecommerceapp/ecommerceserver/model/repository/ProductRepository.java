package com.ecommerceapp.ecommerceserver.model.repository;

import com.ecommerceapp.ecommerceserver.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
