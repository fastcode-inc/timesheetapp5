package com.fastcode.timesheetapp5.addons.reporting.application.dashboard.dto;

import com.fastcode.timesheetapp5.addons.reporting.application.report.dto.FindReportByIdOutput;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindDashboardByIdOutput {

    private String description;
    private String title;
    private Long userId;
    private Long id;
    private Boolean isPublished;
    private Boolean isRefreshed;
    private Long ownerId;
    private String ownerDescriptiveField;
    private List<FindReportByIdOutput> reportDetails;
    private Long versiono;
}
