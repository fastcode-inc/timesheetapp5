package com.fastcode.timesheetapp5.addons.reporting.application.dashboard;

import com.fastcode.timesheetapp5.addons.reporting.application.dashboard.dto.*;
import com.fastcode.timesheetapp5.addons.reporting.application.dashboardversion.dto.CreateDashboardversionInput;
import com.fastcode.timesheetapp5.addons.reporting.application.dashboardversion.dto.CreateDashboardversionOutput;
import com.fastcode.timesheetapp5.addons.reporting.application.dashboardversion.dto.UpdateDashboardversionInput;
import com.fastcode.timesheetapp5.addons.reporting.application.dashboardversion.dto.UpdateDashboardversionOutput;
import com.fastcode.timesheetapp5.addons.reporting.application.report.dto.CreateReportInput;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboard.DashboardEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversion.DashboardversionEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.report.ReportEntity;
import com.fastcode.timesheetapp5.domain.core.authorization.users.UsersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IDashboardMapper {
    @Mappings({ @Mapping(source = "ownerId", target = "userId") })
    CreateDashboardversionInput creatDashboardInputToCreateDashboardversionInput(CreateDashboardInput dashboardDto);

    DashboardEntity createDashboardInputToDashboardEntity(CreateDashboardInput dashboardDto);

    CreateDashboardInput addNewReportToNewDashboardInputTocreatDashboardInput(AddNewReportToNewDashboardInput input);

    CreateDashboardInput addExistingReportToNewDashboardInputTocreatDashboardInput(
        AddExistingReportToNewDashboardInput input
    );

    DashboardEntity addExistingReportToNewDashboardInputToDashboardEntity(AddExistingReportToNewDashboardInput input);

    ReportEntity createDashboardAndReportInputToReportEntity(CreateReportInput reportDto);

    @Mappings(
        {
            @Mapping(source = "entity.id", target = "id"),
            @Mapping(source = "entity.users.id", target = "ownerId"),
            @Mapping(source = "entity.users.username", target = "ownerDescriptiveField"),
        }
    )
    CreateDashboardOutput dashboardEntityAndCreateDashboardversionOutputToCreateDashboardOutput(
        DashboardEntity entity,
        CreateDashboardversionOutput dashboardversion
    );

    @Mappings(
        {
            @Mapping(source = "dashboardversion.dashboardId", target = "id"),
            @Mapping(source = "dashboardversion.userId", target = "ownerId"),
            @Mapping(source = "dashboardversion.users.username", target = "ownerDescriptiveField"),
        }
    )
    CreateDashboardOutput dashboardEntityAndDashboardversionEntityToCreateDashboardOutput(
        DashboardEntity dashboard,
        DashboardversionEntity dashboardversion
    );

    UpdateDashboardversionInput updateDashboardInputToUpdateDashboardversionInput(UpdateDashboardInput dashboardDto);

    @Mappings(
        {
            @Mapping(source = "entity.id", target = "id"),
            @Mapping(source = "entity.users.id", target = "ownerId"),
            @Mapping(source = "entity.users.username", target = "ownerDescriptiveField"),
        }
    )
    UpdateDashboardOutput dashboardEntityAndUpdateDashboardversionOutputToUpdateDashboardOutput(
        DashboardEntity entity,
        UpdateDashboardversionOutput dashboardversion
    );

    DashboardEntity updateDashboardInputToDashboardEntity(UpdateDashboardInput dashboardDto);

    @Mappings({ @Mapping(source = "users.id", target = "ownerId") })
    UpdateDashboardOutput dashboardEntityToUpdateDashboardOutput(DashboardEntity entity);

    @Mappings(
        {
            @Mapping(source = "users.id", target = "ownerId"),
            @Mapping(source = "users.username", target = "ownerDescriptiveField"),
        }
    )
    FindDashboardByIdOutput dashboardEntityToFindDashboardByIdOutput(DashboardEntity entity);

    @Mappings({ @Mapping(source = "ownerId", target = "userId") })
    FindDashboardByIdOutput dashboardOutputToFindDashboardByIdOutput(CreateDashboardOutput entity);

    @Mappings(
        {
            @Mapping(source = "dashboardversion.userId", target = "userId"),
            @Mapping(source = "dashboard.users.id", target = "ownerId"),
            @Mapping(source = "dashboardversion.dashboardId", target = "id"),
            @Mapping(source = "dashboard.versiono", target = "versiono"),
        }
    )
    FindDashboardByIdOutput dashboardEntitiesToFindDashboardByIdOutput(
        DashboardEntity dashboard,
        DashboardversionEntity dashboardversion
    );

    @Mappings(
        {
            @Mapping(source = "dashboardversion.userId", target = "userId"),
            @Mapping(source = "dashboardversion.dashboardId", target = "id"),
        }
    )
    DashboardDetailsOutput dashboardEntitiesToDashboardDetailsOutput(
        DashboardEntity dashboard,
        DashboardversionEntity dashboardversion
    );

    @Mappings(
        { @Mapping(source = "users.id", target = "id"), @Mapping(source = "dashboard.id", target = "dashboardId") }
    )
    GetUsersOutput usersEntityToGetUsersOutput(UsersEntity users, DashboardEntity dashboard);
}
