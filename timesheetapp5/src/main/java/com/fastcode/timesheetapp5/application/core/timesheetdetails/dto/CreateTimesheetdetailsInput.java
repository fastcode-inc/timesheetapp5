package com.fastcode.timesheetapp5.application.core.timesheetdetails.dto;

import java.math.BigDecimal;
import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTimesheetdetailsInput {

    private BigDecimal hours;

    private LocalDateTime workdate;

    private Long taskid;
    private Long timesheetid;
}
