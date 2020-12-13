package com.fastcode.timesheetapp5.application.core.timesheet.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTimesheetOutput {

    private Long id;
    private String notes;
    private LocalDateTime periodendingdate;
    private Long userid;
    private Long usersDescriptiveField;
}
