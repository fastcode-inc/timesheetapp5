package com.fastcode.timesheetapp5.addons.reporting.application.dashboardversionreport;

import com.fastcode.timesheetapp5.addons.reporting.application.dashboardversionreport.dto.*;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversion.DashboardversionEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversionreport.DashboardversionreportEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.report.ReportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IDashboardversionreportMapper {
    DashboardversionreportEntity createReportdashboardInputToDashboardversionreportEntity(
        CreateDashboardversionreportInput reportdashboardDto
    );

    @Mappings(
        { @Mapping(source = "dashboardId", target = "dashboardId"), @Mapping(source = "reportId", target = "reportId") }
    )
    CreateDashboardversionreportOutput reportdashboardEntityToCreateReportdashboardOutput(
        DashboardversionreportEntity entity
    );

    DashboardversionreportEntity updateReportdashboardInputToDashboardversionreportEntity(
        UpdateDashboardversionreportInput reportdashboardDto
    );

    @Mappings(
        { @Mapping(source = "dashboardId", target = "dashboardId"), @Mapping(source = "reportId", target = "reportId") }
    )
    UpdateDashboardversionreportOutput reportdashboardEntityToUpdateReportdashboardOutput(
        DashboardversionreportEntity entity
    );

    FindDashboardversionreportByIdOutput reportdashboardEntityToFindReportdashboardByIdOutput(
        DashboardversionreportEntity entity
    );

    @Mappings(
        {
            @Mapping(source = "reportdashboard.dashboardId", target = "reportdashboardDashboardId"),
            @Mapping(source = "reportdashboard.reportId", target = "reportdashboardReportId"),
        }
    )
    GetDashboardversionOutput dashboardversionEntityToGetDashboardversionOutput(
        DashboardversionEntity dashboardversion,
        DashboardversionreportEntity reportdashboard
    );

    @Mappings(
        {
            @Mapping(source = "reportdashboard.dashboardId", target = "reportdashboardDashboardId"),
            @Mapping(source = "reportdashboard.reportId", target = "reportdashboardReportId"),
        }
    )
    GetReportOutput reportEntityToGetReportOutput(ReportEntity report, DashboardversionreportEntity reportdashboard);
}
