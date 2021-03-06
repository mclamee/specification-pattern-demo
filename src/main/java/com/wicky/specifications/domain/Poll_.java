package com.wicky.specifications.domain;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(Poll.class)
public class Poll_ {
        public static volatile SingularAttribute<Poll, LocalDateTime> startDate;
        public static volatile SingularAttribute<Poll, LocalDateTime> endDate;
        public static volatile SingularAttribute<Poll, LocalDateTime> lockDate;
        public static volatile ListAttribute<Poll, Vote> votes;
}
