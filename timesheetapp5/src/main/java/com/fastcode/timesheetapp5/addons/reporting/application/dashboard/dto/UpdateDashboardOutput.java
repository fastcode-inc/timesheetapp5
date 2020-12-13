package com.fastcode.timesheetapp5.addons.reporting.application.dashboard.dto;

import com.fastcode.timesheetapp5.addons.reporting.application.report.dto.FindReportByIdOutput;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDashboardOutput {

    private String description;
    private Long id;
    private String title;
    private Long ownerId;
    private String ownerDescriptiveField;
    private List<FindReportByIdOutput> reportDetails;
    private Boolean isRefreshed;
    private Long versiono;
}
