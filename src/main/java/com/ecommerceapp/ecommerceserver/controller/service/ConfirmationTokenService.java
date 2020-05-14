package com.ecommerceapp.ecommerceserver.controller.service;

import com.ecommerceapp.ecommerceserver.model.entity.ConfirmationToken;
import com.ecommerceapp.ecommerceserver.model.entity.User;
import com.ecommerceapp.ecommerceserver.model.repository.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenService {
    private ConfirmationTokenRepository tokenRepository;

    public ConfirmationTokenService(ConfirmationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public ConfirmationToken save(User user) {
        return tokenRepository.save(new ConfirmationToken(user));
    }

    public ConfirmationToken getByConfirmationToken(String token) {
        return tokenRepository.findByConfirmationToken(token);
    }
}
