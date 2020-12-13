package com.fastcode.timesheetapp5.domain.core.status;

import java.time.*;
import java.util.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("statusRepository")
public interface IStatusRepository extends JpaRepository<StatusEntity, Long>, QuerydslPredicateExecutor<StatusEntity> {}
