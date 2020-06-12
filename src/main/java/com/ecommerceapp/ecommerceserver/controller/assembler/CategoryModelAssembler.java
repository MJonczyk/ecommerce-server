package com.ecommerceapp.ecommerceserver.controller.assembler;

import com.ecommerceapp.ecommerceserver.controller.CategoryController;
import com.ecommerceapp.ecommerceserver.model.entity.Category;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoryModelAssembler implements RepresentationModelAssembler<Category, EntityModel<Category>> {
    @Override
    public EntityModel<Category> toModel(Category category) {
        return new EntityModel<>(category,
                linkTo(methodOn(CategoryController.class).getOne(category.getId())).withSelfRel(),
                linkTo(methodOn(CategoryController.class).getAll()).withRel("categories")
        );
    }
}
