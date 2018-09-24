package com.wicky.specifications.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Poll {

    @Id
    @GeneratedValue
    private long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime lockDate;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Vote> votes = new ArrayList<>();

    public boolean isActive() {
        return startDate.isBefore(LocalDateTime.now()) && endDate.isAfter(LocalDateTime.now()) && lockDate == null;
    }

    public boolean isPopular() {
        return votes.size() > 5 && lockDate == null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime start) {
        this.startDate = start;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime end) {
        this.endDate = end;
    }

    public LocalDateTime getLockDate() {
        return lockDate;
    }

    public void setLockDate(LocalDateTime locked) {
        this.lockDate = locked;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}