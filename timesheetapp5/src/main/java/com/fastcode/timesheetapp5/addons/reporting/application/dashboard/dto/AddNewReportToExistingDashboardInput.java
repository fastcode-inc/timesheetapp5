package com.fastcode.timesheetapp5.addons.reporting.application.dashboard.dto;

import com.fastcode.timesheetapp5.addons.reporting.application.report.dto.CreateReportInput;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddNewReportToExistingDashboardInput {

    private Long id;
    private String description;
    private String title;
    private Long ownerId;
    private Boolean isPublished;
    List<CreateReportInput> reportDetails = new ArrayList<>();
}
