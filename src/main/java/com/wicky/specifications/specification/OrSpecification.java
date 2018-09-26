package com.wicky.specifications.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class OrSpecification<T> extends AbstractSpecification<T> {

	private Specification<T> first;
	private Specification<T> second;

	public OrSpecification(Specification<T> first, Specification<T> second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public boolean isSatisfiedBy(T t) {
		return first.isSatisfiedBy(t) || second.isSatisfiedBy(t);
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaBuilder cb) {
		return cb.or(
			first.toPredicate(root, cb), 
			second.toPredicate(root, cb)
		);
	}
	
	@Override
	public Class<T> getType() {
		return first.getType();
	}
}
