package com.ecommerceapp.ecommerceserver.controller.assembler;

import com.ecommerceapp.ecommerceserver.controller.ProductController;
import com.ecommerceapp.ecommerceserver.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {
    @Override
    public EntityModel<Product> toModel(Product product) {
        return new EntityModel<>(product,
                linkTo(methodOn(ProductController.class).getOne(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAll("", "", "", 0, 0))
                        .withRel("products")
                );
    }

    public PagedModel<EntityModel<Product>> toPagedModel(List<EntityModel<Product>> products,
                                                         PagedModel.PageMetadata metadata, String search,
                                                         String sortColumn, String sortOrder, int page, int pageSize) {
        if (page == 0 && page < metadata.getTotalPages() - 1)
            return new PagedModel<EntityModel<Product>>(products, metadata,
                    linkTo(methodOn(ProductController.class)
                            .getAll(search, sortColumn, sortOrder, page + 1, pageSize)).withRel("next"));
        else if (page > 0 && page == metadata.getTotalPages() - 1)
            return new PagedModel<EntityModel<Product>>(products, metadata,
                    linkTo(methodOn(ProductController.class)
                            .getAll(search, sortColumn, sortOrder, page - 1, pageSize)).withRel("previous"));
        else if (page < metadata.getTotalPages() && page > 0)
            return new PagedModel<EntityModel<Product>>(products, metadata,
                    linkTo(methodOn(ProductController.class)
                            .getAll(search, sortColumn, sortOrder, page - 1, pageSize)).withRel("previous"),
                    linkTo(methodOn(ProductController.class)
                            .getAll(search, sortColumn, sortOrder, page + 1, pageSize)).withRel("next"));
        else if (page == 0 && page == metadata.getTotalPages() - 1)
            return new PagedModel<EntityModel<Product>>(products, metadata,
                    linkTo(methodOn(ProductController.class)
                    .getAll(search, sortColumn, sortOrder, page, pageSize)).withRel("self"));
        else
            return new PagedModel<EntityModel<Product>>(products, null,
                    linkTo(methodOn(ProductController.class)
                            .getAll("", "", "", 0, 0)).withRel("self"));
    }
}
