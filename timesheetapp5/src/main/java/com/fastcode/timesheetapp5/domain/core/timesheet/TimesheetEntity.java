package com.fastcode.timesheetapp5.domain.core.timesheet;

import com.fastcode.timesheetapp5.domain.core.abstractentity.AbstractEntity;
import com.fastcode.timesheetapp5.domain.core.authorization.users.UsersEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "timesheet")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class TimesheetEntity extends AbstractEntity {

    @Basic
    @Column(name = "periodendingdate", nullable = true)
    private LocalDateTime periodendingdate;

    @Basic
    @Column(name = "notes", nullable = true)
    private String notes;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UsersEntity users;
}
