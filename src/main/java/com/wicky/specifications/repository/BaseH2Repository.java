package com.wicky.specifications.repository;

import com.wicky.specifications.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class BaseH2Repository {

    @PersistenceContext
    protected EntityManager entityManager;

    public <T> List<T> findAllBySpecification(Specification<T> specification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(specification.getType());
        Root<T> root = criteriaQuery.from(specification.getType());

        Predicate predicate = specification.toPredicate(root, criteriaBuilder);

        criteriaQuery.where(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
