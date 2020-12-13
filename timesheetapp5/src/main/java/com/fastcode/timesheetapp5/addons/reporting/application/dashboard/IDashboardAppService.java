package com.fastcode.timesheetapp5.addons.reporting.application.dashboard;

import com.fastcode.timesheetapp5.addons.reporting.application.dashboard.dto.*;
import com.fastcode.timesheetapp5.addons.reporting.application.report.dto.FindReportByIdOutput;
import com.fastcode.timesheetapp5.commons.search.SearchCriteria;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;

public interface IDashboardAppService {
    CreateDashboardOutput create(CreateDashboardInput dashboard);

    void delete(Long id, Long userId);

    void deleteReportFromDashboard(Long dashboardId, Long reportId, Long userId);

    UpdateDashboardOutput update(Long id, UpdateDashboardInput input);

    FindDashboardByIdOutput findById(Long id);

    List<FindDashboardByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;

    List<FindReportByIdOutput> setReportsList(Long dashboardId, Long userId, String version);

    FindDashboardByIdOutput findByDashboardIdAndUsersId(Long dashboardId, Long userId, String version);

    List<DashboardDetailsOutput> getDashboards(Long userId, String search, Pageable pageable) throws Exception;

    List<DashboardDetailsOutput> getAvailableDashboards(Long userId, Long reportId, String search, Pageable pageable)
        throws Exception;

    FindDashboardByIdOutput publishDashboard(Long userId, Long dashboardId);

    FindDashboardByIdOutput refreshDashboard(Long userId, Long dashboardId);

    FindDashboardByIdOutput addNewReportsToNewDashboard(AddNewReportToNewDashboardInput input);

    FindDashboardByIdOutput addNewReportsToExistingDashboard(AddNewReportToExistingDashboardInput input);

    FindDashboardByIdOutput addExistingReportsToNewDashboard(AddExistingReportToNewDashboardInput input);

    FindDashboardByIdOutput addExistingReportsToExistingDashboard(AddExistingReportToExistingDashboardInput input);

    Map<String, String> parseReportdashboardJoinColumn(String keysString);
}
