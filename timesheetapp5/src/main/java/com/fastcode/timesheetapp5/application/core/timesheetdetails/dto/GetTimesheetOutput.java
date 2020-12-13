package com.fastcode.timesheetapp5.application.core.timesheetdetails.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTimesheetOutput {

    private Long id;
    private String notes;
    private LocalDateTime periodendingdate;
    private Long timesheetdetailsId;
}
