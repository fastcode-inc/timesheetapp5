package com.fastcode.timesheetapp5.application.core.timesheetdetails.dto;

import java.math.BigDecimal;
import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTimesheetdetailsOutput {

    private BigDecimal hours;
    private Long id;
    private LocalDateTime workdate;
    private Long taskid;
    private Long taskDescriptiveField;
    private Long timesheetid;
    private Long timesheetDescriptiveField;
}
