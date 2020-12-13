package com.fastcode.timesheetapp5.application.core.timesheetstatus.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTimesheetstatusInput {

    private String notes;

    private LocalDateTime statuschangedate;

    @NotNull(message = "statusid Should not be null")
    private Long statusid;

    @NotNull(message = "timesheetid Should not be null")
    private Long timesheetid;

    private Long versiono;
}
