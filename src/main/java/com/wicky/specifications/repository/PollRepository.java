package com.wicky.specifications.repository;

import com.wicky.specifications.domain.Poll;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PollRepository extends BaseH2Repository {

    @Transactional
    public void save(Poll poll) {
        this.entityManager.persist(poll);
		this.entityManager.flush();
    }

}
