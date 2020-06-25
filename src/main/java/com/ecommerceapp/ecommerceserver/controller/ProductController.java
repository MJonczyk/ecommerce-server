package com.ecommerceapp.ecommerceserver.controller;

import com.ecommerceapp.ecommerceserver.controller.assembler.ProductModelAssembler;
import com.ecommerceapp.ecommerceserver.controller.service.ProductService;
import com.ecommerceapp.ecommerceserver.model.entity.Product;
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
public class ProductController {
    private ProductService productService;
    private ProductModelAssembler productModelAssembler;

    public  ProductController(ProductService productService, ProductModelAssembler productModelAssembler) {
        this.productService = productService;
        this.productModelAssembler = productModelAssembler;
    }

    @GetMapping("/products")
    public CollectionModel<EntityModel<Product>> getAll() {
        List<EntityModel<Product>> products = productService.getAll()
                .stream()
                .map(productModelAssembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(products, linkTo(methodOn(ProductController.class).getAll()).withSelfRel());
    }

    @GetMapping("/products/{id}")
    public EntityModel<Product> getOne(@PathVariable Long id) {
        return productModelAssembler.toModel(productService.getOne(id));
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/products")
    public ResponseEntity add(@RequestBody Product product) {
        EntityModel<Product> productEntityModel = productModelAssembler.toModel(productService.save(product));

        return ResponseEntity.created(productEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(productEntityModel);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/products/{id}")
    public ResponseEntity<?> edit(@RequestBody Product product, @PathVariable Long id) {
        EntityModel<Product> productEntityModel = productModelAssembler.toModel(productService.edit(product, id));

        return ResponseEntity.created(productEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(productEntityModel);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
