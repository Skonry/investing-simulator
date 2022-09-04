package com.investingsimulator.instrument;


import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;

public class InstrumentSpecification implements Specification<Instrument> {

    private SearchCriteria searchCriteria;

    public InstrumentSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Instrument> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (searchCriteria.operation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
                    root.<String> get(searchCriteria.key()), searchCriteria.value().toString());
        }
        else if (searchCriteria.operation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.<String> get(searchCriteria.key()), searchCriteria.value().toString());
        }
        else if (searchCriteria.operation().equalsIgnoreCase(":")) {
            if (root.get(searchCriteria.key()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(searchCriteria.key()), "%" + searchCriteria.value() + "%");
            } else {
                return builder.equal(root.get(searchCriteria.key()), searchCriteria.value());
            }
        }
        return null;
    }
}
