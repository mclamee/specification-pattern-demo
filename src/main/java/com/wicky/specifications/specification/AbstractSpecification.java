package com.wicky.specifications.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;

abstract public class AbstractSpecification<T> implements Specification<T> {

	@Override
	public boolean isSatisfiedBy(T t) {
		throw new NotImplementedException();
	}

	@Override
	public Predicate toPredicate(Root<T> poll, CriteriaBuilder cb) {
		throw new NotImplementedException();
	}

	@Override
	public Specification<T> and(Specification<T> other) {
		return new AndSpecification<>(this, other);
	}

	@Override
	public Specification<T> or(Specification<T> other) {
		return new OrSpecification<>(this, other);
	}

	@Override
	public Specification<T> not() {
		return new NotSpecification<>(this);
	}

	@Override
	public Class<T> getType() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		return (Class<T>)type.getActualTypeArguments()[0];
	}
}
