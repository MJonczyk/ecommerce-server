package com.ecommerceapp.ecommerceserver.controller.service;

import com.ecommerceapp.ecommerceserver.model.entity.Category;
import com.ecommerceapp.ecommerceserver.model.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getOne(Long id) {
        return categoryRepository.getOne(id);
    }

    public Category save(Category newCategory) {
        if (newCategory != null) {
            return categoryRepository.save(newCategory);
        }
        return null;
    }

    public Category edit(Category editedCategory, Long id) {
        return categoryRepository.findById(id)
                .map( category -> {
                    category.setName(editedCategory.getName());
                    return categoryRepository.save(editedCategory);
                })
                .orElseGet( () -> {
                    editedCategory.setId(id);
                    return categoryRepository.save(editedCategory);
                });
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
