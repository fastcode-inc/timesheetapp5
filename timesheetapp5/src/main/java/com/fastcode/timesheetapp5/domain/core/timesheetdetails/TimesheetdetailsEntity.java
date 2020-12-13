package com.fastcode.timesheetapp5.domain.core.timesheetdetails;

import com.fastcode.timesheetapp5.domain.core.abstractentity.AbstractEntity;
import com.fastcode.timesheetapp5.domain.core.task.TaskEntity;
import com.fastcode.timesheetapp5.domain.core.timesheet.TimesheetEntity;
import java.math.BigDecimal;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "timesheetdetails")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class TimesheetdetailsEntity extends AbstractEntity {

    @Basic
    @Column(name = "hours", nullable = true)
    private BigDecimal hours;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "workdate", nullable = true)
    private LocalDateTime workdate;

    @ManyToOne
    @JoinColumn(name = "timesheetid")
    private TimesheetEntity timesheet;

    @ManyToOne
    @JoinColumn(name = "taskid")
    private TaskEntity task;
}
