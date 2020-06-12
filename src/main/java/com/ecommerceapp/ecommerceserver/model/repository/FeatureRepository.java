package com.ecommerceapp.ecommerceserver.model.repository;

import com.ecommerceapp.ecommerceserver.model.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
}
