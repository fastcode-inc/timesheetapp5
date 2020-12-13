package com.fastcode.timesheetapp5.addons.reporting.application.dashboardversion;

import com.fastcode.timesheetapp5.addons.reporting.application.dashboardversion.dto.*;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversion.DashboardversionEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversionreport.DashboardversionreportEntity;
import com.fastcode.timesheetapp5.domain.core.authorization.users.UsersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IDashboardversionMapper {
    DashboardversionEntity createDashboardversionInputToDashboardversionEntity(
        CreateDashboardversionInput dashboardversionDto
    );

    @Mappings(
        {
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "users.username", target = "userDescriptiveField"),
        }
    )
    CreateDashboardversionOutput dashboardversionEntityToCreateDashboardversionOutput(DashboardversionEntity entity);

    DashboardversionEntity updateDashboardversionInputToDashboardversionEntity(
        UpdateDashboardversionInput dashboardversionDto
    );

    @Mappings(
        {
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "users.username", target = "userDescriptiveField"),
        }
    )
    UpdateDashboardversionOutput dashboardversionEntityToUpdateDashboardversionOutput(DashboardversionEntity entity);

    @Mappings(
        {
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "users.username", target = "userDescriptiveField"),
        }
    )
    FindDashboardversionByIdOutput dashboardversionEntityToFindDashboardversionByIdOutput(
        DashboardversionEntity entity
    );

    @Mappings(
        { @Mapping(source = "userId", target = "userId"), @Mapping(source = "dversion", target = "dashboardVersion") }
    )
    DashboardversionEntity dashboardversionEntityToDashboardversionEntity(
        DashboardversionEntity entity,
        Long userId,
        String dversion
    );

    @Mappings(
        { @Mapping(source = "userId", target = "userId"), @Mapping(source = "dversion", target = "dashboardVersion") }
    )
    DashboardversionreportEntity dashboardversionreportEntityToDashboardversionreportEntity(
        DashboardversionreportEntity dashboardreport,
        Long userId,
        String dversion
    );

    @Mappings(
        {
            @Mapping(source = "users.id", target = "id"),
            @Mapping(source = "dashboardversion.dashboardVersion", target = "dashboardVersion"),
        }
    )
    GetUsersOutput usersEntityToGetUsersOutput(UsersEntity users, DashboardversionEntity dashboardversion);
}
