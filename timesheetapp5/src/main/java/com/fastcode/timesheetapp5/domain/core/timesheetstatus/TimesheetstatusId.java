package com.fastcode.timesheetapp5.domain.core.timesheetstatus;

import java.io.Serializable;
import java.time.*;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TimesheetstatusId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long timesheetid;
    private Long statusid;

    public TimesheetstatusId(Long statusid, Long timesheetid) {
        this.timesheetid = timesheetid;
        this.statusid = statusid;
    }
}
