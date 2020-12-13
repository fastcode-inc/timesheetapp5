package com.fastcode.timesheetapp5.domain.core.authorization.usersrole;

import java.time.*;
import java.util.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("usersroleRepository")
public interface IUsersroleRepository
    extends JpaRepository<UsersroleEntity, UsersroleId>, QuerydslPredicateExecutor<UsersroleEntity> {
    List<UsersroleEntity> findByUsersId(Long usersId);

    List<UsersroleEntity> findByRoleId(Long roleId);
}
