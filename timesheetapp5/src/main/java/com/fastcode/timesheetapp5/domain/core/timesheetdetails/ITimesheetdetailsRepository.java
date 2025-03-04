package com.fastcode.timesheetapp5.domain.core.timesheetdetails;

import java.time.*;
import java.util.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("timesheetdetailsRepository")
public interface ITimesheetdetailsRepository
    extends JpaRepository<TimesheetdetailsEntity, Long>, QuerydslPredicateExecutor<TimesheetdetailsEntity> {}
