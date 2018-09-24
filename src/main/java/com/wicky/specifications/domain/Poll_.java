package com.wicky.specifications.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import java.util.List;

@StaticMetamodel(Poll.class)
public class Poll_ {
        public static volatile SingularAttribute<Poll, LocalDateTime> startDate;
        public static volatile SingularAttribute<Poll, LocalDateTime> endDate;
        public static volatile SingularAttribute<Poll, LocalDateTime> lockDate;
        public static volatile SingularAttribute<Poll, List<Vote>> votes;
}
