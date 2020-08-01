package com.ecommerceapp.ecommerceserver.controller.service;

import com.ecommerceapp.ecommerceserver.controller.service.specification.ProductSpecificationBuilder;
import com.ecommerceapp.ecommerceserver.controller.service.specification.SpecificationConjunction;
import com.ecommerceapp.ecommerceserver.model.entity.Product;
import com.ecommerceapp.ecommerceserver.model.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<Product> getAllWithSearchingPagingAndSorting(String search, String sortColumn, String sortOrder,
                                                             int page, int pageSize) {
        Specification<Product> spec = null;

        if (!search.equals("")) {
            String[] keys = {"name", "description", "category_name"};
            ProductSpecificationBuilder builder = new ProductSpecificationBuilder();
            for (String key : keys)
                builder = builder.with(key, ":", search, SpecificationConjunction.OR);
            spec = builder.build();
        }

        if (sortColumn.equals(""))
            sortColumn = "id";
        else if (sortColumn.equals("category"))
            sortColumn = "category.name";

        Pageable pageable = PageRequest.of(
                page,
                pageSize < 1 ? Integer.MAX_VALUE : pageSize,
                sortOrder.equals("desc") ? Sort.by(sortColumn).descending() : Sort.by(sortColumn).ascending()
        );

        return productRepository.findAll(spec, pageable);
    }

    public Product getOne(Long id) {
        return productRepository.getOne(id);
    }

    public Product save(Product newProduct) {
        if (newProduct != null) {
            if (newProduct.getDetails() != null)
                newProduct.getDetails().forEach( productDetail -> productDetail.setProduct(newProduct) );
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
