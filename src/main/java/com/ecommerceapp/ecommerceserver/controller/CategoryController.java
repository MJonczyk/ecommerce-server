package com.ecommerceapp.ecommerceserver.controller;

import com.ecommerceapp.ecommerceserver.controller.assembler.CategoryModelAssembler;
import com.ecommerceapp.ecommerceserver.controller.service.CategoryService;
import com.ecommerceapp.ecommerceserver.model.entity.Category;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CategoryController {
    private CategoryService categoryService;
    private CategoryModelAssembler categoryModelAssembler;

    public CategoryController(CategoryService categoryService, CategoryModelAssembler categoryModelAssembler) {
        this.categoryService = categoryService;
        this.categoryModelAssembler = categoryModelAssembler;
    }

    @GetMapping("/categories")
    public CollectionModel<EntityModel<Category>> getAll() {
        List<EntityModel<Category>> categories = categoryService.getAll()
                .stream()
                .map(categoryModelAssembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(categories, linkTo(methodOn(CategoryController.class).getAll()).withSelfRel());
    }

    @GetMapping("/categories/{id}")
    public EntityModel<Category> getOne(@PathVariable Long id) {
        return categoryModelAssembler.toModel(categoryService.getOne(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/category")
    public ResponseEntity add(@RequestBody Category category) {
        EntityModel<Category> categoryEntityModel = categoryModelAssembler.toModel(categoryService.save(category));
        return ResponseEntity.created(categoryEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(categoryEntityModel);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/categories/{id}")
    public ResponseEntity<?> edit(@RequestBody Category category, @PathVariable Long id) {
        EntityModel<Category> categoryEntityModel = categoryModelAssembler.toModel(categoryService.edit(category, id));

        return ResponseEntity.created(categoryEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(categoryEntityModel);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/categories/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        categoryService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
