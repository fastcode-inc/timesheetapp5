package com.fastcode.timesheetapp5.domain.core.timesheetstatus;

import com.fastcode.timesheetapp5.domain.core.abstractentity.AbstractEntity;
import com.fastcode.timesheetapp5.domain.core.status.StatusEntity;
import com.fastcode.timesheetapp5.domain.core.timesheet.TimesheetEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "timesheetstatus")
@IdClass(TimesheetstatusId.class)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class TimesheetstatusEntity extends AbstractEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "timesheetid", nullable = false)
    private Long timesheetid;

    @Basic
    @Column(name = "notes", nullable = true)
    private String notes;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "statusid", nullable = false)
    private Long statusid;

    @Basic
    @Column(name = "statuschangedate", nullable = true)
    private LocalDateTime statuschangedate;

    @ManyToOne
    @JoinColumn(name = "timesheetid", insertable = false, updatable = false)
    private TimesheetEntity timesheet;

    @ManyToOne
    @JoinColumn(name = "statusid", insertable = false, updatable = false)
    private StatusEntity status;
}
