package com.fastcode.timesheetapp5.addons.reporting.application.dashboardversion;

import com.fastcode.timesheetapp5.addons.reporting.application.dashboardversion.dto.*;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversion.DashboardversionId;
import com.fastcode.timesheetapp5.commons.search.SearchCriteria;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;

public interface IDashboardversionAppService {
    CreateDashboardversionOutput create(CreateDashboardversionInput dashboardversion);

    void delete(DashboardversionId id);

    UpdateDashboardversionOutput update(DashboardversionId id, UpdateDashboardversionInput input);

    FindDashboardversionByIdOutput findById(DashboardversionId id);

    List<FindDashboardversionByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;

    Map<String, String> parseReportdashboardJoinColumn(String keysString);

    //User
    GetUsersOutput getUsers(DashboardversionId dashboardversionid);
}
