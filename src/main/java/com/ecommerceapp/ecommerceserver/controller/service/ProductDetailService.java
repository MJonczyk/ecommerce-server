package com.ecommerceapp.ecommerceserver.controller.service;

import com.ecommerceapp.ecommerceserver.model.entity.ProductDetail;
import com.ecommerceapp.ecommerceserver.model.repository.ProductDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailService {
    private ProductDetailRepository productDetailRepository;

    public ProductDetailService(ProductDetailRepository productDetailRepository) {
        this.productDetailRepository = productDetailRepository;
    }

    public List<ProductDetail> getAll() {
        return productDetailRepository.findAll();
    }

    public ProductDetail getOne(Long id) {
        return productDetailRepository.getOne(id);
    }

    public ProductDetail save(ProductDetail newProductDetail) {
        if (newProductDetail != null) {
            return productDetailRepository.save(newProductDetail);
        }
        return null;
    }

    public ProductDetail edit(ProductDetail editedProductDetail, Long id) {
        return productDetailRepository.findById(id)
                .map( productDetail -> {
                    productDetail.setValue(editedProductDetail.getValue());
                    return productDetailRepository.save(productDetail);
                })
                .orElseGet( () -> {
                    editedProductDetail.setId(id);
                    return productDetailRepository.save(editedProductDetail);
                });
    }

    public void delete(Long id) {
        productDetailRepository.deleteById(id);
    }
}
