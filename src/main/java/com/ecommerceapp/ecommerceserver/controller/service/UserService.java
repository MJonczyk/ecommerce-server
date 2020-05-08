package com.ecommerceapp.ecommerceserver.controller.service;

import com.ecommerceapp.ecommerceserver.model.entity.User;
import com.ecommerceapp.ecommerceserver.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() { return this.userRepository.findAll(); }

    public User getOne(Long id) { return this.userRepository.getOne(id); }

    public User getOneByUsermame(String username) {
        return this.userRepository.findUserByUsername(username).orElse(null);
    }


    public User save(User newUser) {
        if(userRepository.findUserByUsername(newUser.getUsername()).isPresent() ||
                userRepository.findUserByEmail(newUser.getEmail()).isPresent())
            return null;
        return userRepository.save(newUser);
    }

    public User edit(User editedUser, Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setEmail(editedUser.getEmail());
                    user.setPassword(editedUser.getPassword());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    editedUser.setId(id);
                    return userRepository.save(editedUser);
                });
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
