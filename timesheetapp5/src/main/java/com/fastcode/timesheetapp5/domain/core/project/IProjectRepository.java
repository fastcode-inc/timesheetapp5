package com.fastcode.timesheetapp5.domain.core.project;

import java.time.*;
import java.util.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("projectRepository")
public interface IProjectRepository
    extends JpaRepository<ProjectEntity, Long>, QuerydslPredicateExecutor<ProjectEntity> {}
