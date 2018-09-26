package com.wicky.specifications.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class NotSpecification<T> extends AbstractSpecification<T> {

	private Specification<T> other;

	public NotSpecification(Specification<T> other) {
		this.other = other;
	}
	
	@Override
	public boolean isSatisfiedBy(T candidate) {
		return !other.isSatisfiedBy(candidate);
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaBuilder cb) {
		return cb.not(other.toPredicate(root, cb));
	}
	
	@Override
	public Class<T> getType() {
		return other.getType();
	}
}
