package com.fastcode.timesheetapp5.domain.core.authorization.role;

import com.fastcode.timesheetapp5.domain.core.abstractentity.AbstractEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class RoleEntity extends AbstractEntity {

    @Basic
    @Column(name = "display_name", nullable = false, length = 255)
    private String displayName;

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
}
