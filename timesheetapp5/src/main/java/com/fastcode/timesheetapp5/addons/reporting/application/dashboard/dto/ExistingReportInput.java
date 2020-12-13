package com.fastcode.timesheetapp5.addons.reporting.application.dashboard.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExistingReportInput {

    @NotNull
    private Long id;

    private String reportWidth;
}
