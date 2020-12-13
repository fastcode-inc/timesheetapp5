package com.fastcode.timesheetapp5.domain.core.authorization.userspreference;

import com.fastcode.timesheetapp5.domain.core.abstractentity.AbstractEntity;
import com.fastcode.timesheetapp5.domain.core.authorization.users.UsersEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "userspreference")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class UserspreferenceEntity extends AbstractEntity {

    @Basic
    @Column(name = "theme", nullable = false, length = 256)
    private String theme;

    @Basic
    @Column(name = "language", nullable = false, length = 256)
    private String language;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id")
    private UsersEntity users;
}
