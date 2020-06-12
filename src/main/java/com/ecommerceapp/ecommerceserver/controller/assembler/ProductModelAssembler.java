package com.ecommerceapp.ecommerceserver.controller.assembler;

import com.ecommerceapp.ecommerceserver.controller.ProductController;
import com.ecommerceapp.ecommerceserver.model.entity.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {
    @Override
    public EntityModel<Product> toModel(Product product) {
        return new EntityModel<>(product,
                linkTo(methodOn(ProductController.class).getOne(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAll()).withRel("products")
                );
    }
}
