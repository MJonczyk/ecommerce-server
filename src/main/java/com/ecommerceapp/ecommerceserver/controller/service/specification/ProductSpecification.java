package com.ecommerceapp.ecommerceserver.controller.service.specification;

import com.ecommerceapp.ecommerceserver.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
@NoArgsConstructor
public class ProductSpecification implements Specification<Product> {
    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (criteria.getKey().contains("_")) {
            if (criteria.getOperation().equals("=")) {
                String[] path = criteria.getKey().split("_");
                return criteriaBuilder.equal(root.get(path[0]).get(path[1]), criteria.getValue());
            } else if (criteria.getOperation().equals(":")) {
                String[] path = criteria.getKey().split("_");
                return criteriaBuilder.like(root.get(path[0]).get(path[1]), "%" + criteria.getValue() + "%");
            } else {
                return null;
            }
        } else {
            if (criteria.getOperation().equals("=")) {
                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            } else if (criteria.getOperation().equals(":")) {
                return criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return null;
            }
        }
    }
}
