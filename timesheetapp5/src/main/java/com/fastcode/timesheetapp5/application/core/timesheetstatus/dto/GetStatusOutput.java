package com.fastcode.timesheetapp5.application.core.timesheetstatus.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetStatusOutput {

    private String description;
    private Long id;
    private Long timesheetstatusStatusid;
    private Long timesheetstatusTimesheetid;
}
