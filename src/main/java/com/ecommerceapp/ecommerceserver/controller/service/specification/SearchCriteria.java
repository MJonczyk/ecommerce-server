package com.ecommerceapp.ecommerceserver.controller.service.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
    private SpecificationConjunction conjunction;

    public boolean isOrPredicate() {
        return conjunction == SpecificationConjunction.OR;
    }
}
