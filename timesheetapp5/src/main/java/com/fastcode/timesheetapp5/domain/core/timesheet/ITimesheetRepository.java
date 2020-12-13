package com.fastcode.timesheetapp5.domain.core.timesheet;

import java.time.*;
import java.util.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("timesheetRepository")
public interface ITimesheetRepository
    extends JpaRepository<TimesheetEntity, Long>, QuerydslPredicateExecutor<TimesheetEntity> {}
