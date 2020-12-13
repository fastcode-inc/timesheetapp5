package com.fastcode.timesheetapp5.domain.core.authorization.rolepermission;

import java.time.*;
import java.util.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("rolepermissionRepository")
public interface IRolepermissionRepository
    extends JpaRepository<RolepermissionEntity, RolepermissionId>, QuerydslPredicateExecutor<RolepermissionEntity> {
    List<RolepermissionEntity> findByRoleId(Long value);
}
