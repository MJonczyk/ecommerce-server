package com.ecommerceapp.ecommerceserver.controller.service;

import com.ecommerceapp.ecommerceserver.model.entity.Product;
import com.ecommerceapp.ecommerceserver.model.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getOne(Long id) {
        return productRepository.getOne(id);
    }

    public Product save(Product newProduct) {
        if (newProduct != null) {
            return productRepository.save(newProduct);
        }
        return null;
    }

    public Product edit(Product editedProduct, Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setDescription(editedProduct.getDescription());
                    product.setName(editedProduct.getName());
                    return productRepository.save(product);
                })
                .orElseGet(() -> {
                    editedProduct.setId(id);
                    return productRepository.save(editedProduct);
                });
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
