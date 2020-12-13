package com.fastcode.timesheetapp5.addons.reporting.application.dashboardversionreport;

import com.fastcode.timesheetapp5.addons.reporting.application.dashboard.dto.CreateDashboardOutput;
import com.fastcode.timesheetapp5.addons.reporting.application.dashboardversionreport.dto.*;
import com.fastcode.timesheetapp5.addons.reporting.application.report.dto.CreateReportOutput;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboard.DashboardEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversionreport.DashboardversionreportId;
import com.fastcode.timesheetapp5.addons.reporting.domain.report.ReportEntity;
import com.fastcode.timesheetapp5.commons.search.SearchCriteria;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface IDashboardversionreportAppService {
    CreateDashboardversionreportOutput create(CreateDashboardversionreportInput reportdashboard);

    void delete(DashboardversionreportId reportdashboardId);

    Boolean addReportsToDashboard(DashboardEntity dashboard, List<ReportEntity> reportsList);

    UpdateDashboardversionreportOutput update(
        DashboardversionreportId reportdashboardId,
        UpdateDashboardversionreportInput input
    );

    FindDashboardversionreportByIdOutput findById(DashboardversionreportId reportdashboardId);

    Boolean addReportsToDashboardRunningversion(CreateDashboardOutput dashboard, List<CreateReportOutput> reportsList);

    Boolean addReportsToDashboardPublishedversion(
        CreateDashboardOutput dashboard,
        List<CreateReportOutput> reportsList
    );

    List<FindDashboardversionreportByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;

    DashboardversionreportId parseReportdashboardKey(String keysString);

    //Dashboard
    GetDashboardversionOutput getDashboard(DashboardversionreportId reportdashboardId);

    //Report
    GetReportOutput getReport(DashboardversionreportId reportdashboardId);
}
