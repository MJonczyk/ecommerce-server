package com.ecommerceapp.ecommerceserver.model.repository;

import com.ecommerceapp.ecommerceserver.model.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
}
