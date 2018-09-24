package com.wicky.specifications.specification;

import com.wicky.specifications.domain.Poll;
import com.wicky.specifications.domain.Poll_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

public class IsCurrentlyRunning extends AbstractSpecification<Poll> {

	@Override
	public boolean isSatisfiedBy(Poll poll) {
		return poll.getStartDate().isBefore(LocalDateTime.now())
				&& poll.getEndDate().isAfter(LocalDateTime.now())
				&& poll.getLockDate() == null;
	}

	@Override
	public Predicate toPredicate(Root<Poll> poll, CriteriaBuilder cb) {
		LocalDateTime now = LocalDateTime.now();
		return cb.and(
			cb.lessThan(poll.get(Poll_.startDate), now),
			cb.greaterThan(poll.get(Poll_.endDate), now),
			cb.isNull(poll.get(Poll_.lockDate))
		);
	}

}
