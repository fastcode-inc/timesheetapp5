package com.fastcode.timesheetapp5.application.core.timesheet.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTimesheetInput {

    @NotNull(message = "id Should not be null")
    private Long id;

    private String notes;

    private LocalDateTime periodendingdate;

    private Long userid;
    private Long versiono;
}
