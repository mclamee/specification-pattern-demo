package com.wicky.specifications;

import com.wicky.specifications.domain.Poll;
import com.wicky.specifications.domain.Vote;
import com.wicky.specifications.repository.PollRepository;
import com.wicky.specifications.specification.IsCurrentlyRunning;
import com.wicky.specifications.specification.IsPopular;
import com.wicky.specifications.specification.Specification;
import javafx.application.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class ApplicationTest {

	@Autowired
	private PollRepository pollRepository;

	private Poll poll;
	private Poll currentlyRunningPoll;
	private Poll popularPoll;
	private Poll popularAndCurrentlyRunningPoll;


	@Before
	public void before() {
		this.poll = this.createPoll(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(11), 0);
		this.currentlyRunningPoll = this.createPoll(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(9), 0);
		this.popularPoll = this.createPoll(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(11), 105);
		this.popularAndCurrentlyRunningPoll = this.createPoll(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(9), 105);
	}

	private Poll createPoll(LocalDateTime start, LocalDateTime end, int numberOfVotes) {
		Poll poll = new Poll();
		poll.setStartDate(start);
		poll.setEndDate(end);
		for (int i = 0; i < numberOfVotes; i++) {
			poll.getVotes().add(new Vote());
		}
		return poll;
	}

	@Test
	public void isSatisfiedBy() {
		assertFalse(new IsPopular().isSatisfiedBy(this.poll));
		assertFalse(new IsPopular().isSatisfiedBy(this.currentlyRunningPoll));
		assertTrue(new IsPopular().isSatisfiedBy(this.popularPoll));
		assertTrue(new IsPopular().isSatisfiedBy(this.popularAndCurrentlyRunningPoll));

		assertFalse(new IsCurrentlyRunning().isSatisfiedBy(this.poll));
		assertTrue(new IsCurrentlyRunning().isSatisfiedBy(this.currentlyRunningPoll));
		assertFalse(new IsCurrentlyRunning().isSatisfiedBy(this.popularPoll));
		assertTrue(new IsCurrentlyRunning().isSatisfiedBy(this.popularAndCurrentlyRunningPoll));

		Specification<Poll> currentlyRunningAndPopular = new IsCurrentlyRunning().and(new IsPopular());
		assertFalse(currentlyRunningAndPopular.isSatisfiedBy(this.poll));
		assertFalse(currentlyRunningAndPopular.isSatisfiedBy(this.currentlyRunningPoll));
		assertFalse(currentlyRunningAndPopular.isSatisfiedBy(this.popularPoll));
		assertTrue(currentlyRunningAndPopular.isSatisfiedBy(this.popularAndCurrentlyRunningPoll));
	}

	@Test
	public void toPredicate() {
		this.pollRepository.save(this.poll);
		this.pollRepository.save(this.currentlyRunningPoll);
		this.pollRepository.save(this.popularPoll);
		this.pollRepository.save(this.popularAndCurrentlyRunningPoll);

		List<Poll> popularPolls = this.pollRepository.findAllBySpecification(new IsPopular());
		assertEquals(2, popularPolls.size());
		assertEquals(this.popularPoll.getId(), popularPolls.get(0).getId());
		assertEquals(this.popularAndCurrentlyRunningPoll.getId(), popularPolls.get(1).getId());

		List<Poll> currentlyRunningPolls = this.pollRepository.findAllBySpecification(new IsCurrentlyRunning());
		assertEquals(2, currentlyRunningPolls.size());
		assertEquals(this.currentlyRunningPoll.getId(), currentlyRunningPolls.get(0).getId());
		assertEquals(this.popularAndCurrentlyRunningPoll.getId(), currentlyRunningPolls.get(1).getId());

		Specification<Poll> currentlyRunningAndPopular = new IsCurrentlyRunning().and(new IsPopular());
		List<Poll> currentlyRunningAndPopularPolls = this.pollRepository.findAllBySpecification(currentlyRunningAndPopular);
		assertEquals(1, currentlyRunningAndPopularPolls.size());
		assertEquals(this.popularAndCurrentlyRunningPoll.getId(), currentlyRunningAndPopularPolls.get(0).getId());
	}
}
