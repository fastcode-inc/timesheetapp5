package com.fastcode.timesheetapp5.application.core.timesheetstatus.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTimesheetstatusOutput {

    private String notes;
    private LocalDateTime statuschangedate;
    private Long statusid;
    private Long timesheetid;
    private Long statusDescriptiveField;
    private Long timesheetDescriptiveField;
}
