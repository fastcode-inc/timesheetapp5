package com.fastcode.timesheetapp5.domain.core.authorization.usersrole;

import com.fastcode.timesheetapp5.domain.core.abstractentity.AbstractEntity;
import com.fastcode.timesheetapp5.domain.core.authorization.role.RoleEntity;
import com.fastcode.timesheetapp5.domain.core.authorization.users.UsersEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usersrole")
@IdClass(UsersroleId.class)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class UsersroleEntity extends AbstractEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "users_id", nullable = false)
    private Long usersId;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "users_id", insertable = false, updatable = false)
    private UsersEntity users;
}
