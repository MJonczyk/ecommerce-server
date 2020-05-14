package com.ecommerceapp.ecommerceserver.model.repository;

import com.ecommerceapp.ecommerceserver.model.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    ConfirmationToken findByConfirmationToken(String token);
}
