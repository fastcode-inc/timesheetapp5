package com.fastcode.timesheetapp5.application.core.timesheetdetails.dto;

import java.math.BigDecimal;
import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTimesheetdetailsInput {

    private BigDecimal hours;

    @NotNull(message = "id Should not be null")
    private Long id;

    private LocalDateTime workdate;

    private Long taskid;
    private Long timesheetid;
    private Long versiono;
}
