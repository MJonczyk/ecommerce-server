package com.ecommerceapp.ecommerceserver.controller.service.specification;

import com.ecommerceapp.ecommerceserver.model.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductSpecificationBuilder {
    private List<SearchCriteria> params;

    public ProductSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public ProductSpecificationBuilder with(String key, String operation, Object value,
                                            SpecificationConjunction conjunction) {
        params.add(new SearchCriteria(key, operation, value, conjunction));
        return this;
    }

    public Specification<Product> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(ProductSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 0;i < params.size();i++) {
            result = params.get(i).isOrPredicate() ? Specification.where(result).or(specs.get(i))
                    : Specification.where(result).and(specs.get(i));
        }

        return result;
    }
}
