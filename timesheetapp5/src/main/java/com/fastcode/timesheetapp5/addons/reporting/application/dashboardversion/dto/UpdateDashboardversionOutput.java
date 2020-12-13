package com.fastcode.timesheetapp5.addons.reporting.application.dashboardversion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDashboardversionOutput {

    private String description;
    private Long id;
    private String title;
    private Boolean isRefreshed;
    private Long userId;
    private String userDescriptiveField;
}
