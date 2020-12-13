package com.fastcode.timesheetapp5.addons.reporting.application.report;

import com.fastcode.timesheetapp5.addons.reporting.application.report.dto.*;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.CreateReportversionInput;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.CreateReportversionOutput;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.UpdateReportversionInput;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.UpdateReportversionOutput;
import com.fastcode.timesheetapp5.addons.reporting.domain.report.ReportEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.reportversion.ReportversionEntity;
import com.fastcode.timesheetapp5.domain.core.authorization.users.UsersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IReportMapper {
    ReportEntity createReportInputToReportEntity(CreateReportInput reportDto);

    @Mappings({ @Mapping(source = "ownerId", target = "userId") })
    CreateReportversionInput createReportInputToCreateReportversionInput(CreateReportInput reportDto);

    @Mappings(
        {
            @Mapping(source = "entity.id", target = "id"),
            @Mapping(source = "entity.users.id", target = "ownerId"),
            @Mapping(source = "entity.users.username", target = "ownerDescriptiveField"),
        }
    )
    CreateReportOutput reportEntityAndCreateReportversionOutputToCreateReportOutput(
        ReportEntity entity,
        CreateReportversionOutput reportversionOutput
    );

    @Mappings(
        {
            @Mapping(source = "report.id", target = "id"),
            @Mapping(source = "report.users.id", target = "ownerId"),
            @Mapping(source = "report.users.username", target = "ownerDescriptiveField"),
        }
    )
    CreateReportOutput reportEntityAndReportversionEntityToCreateReportOutput(
        ReportEntity report,
        ReportversionEntity reportversion
    );

    FindReportByIdOutput createReportOutputToFindReportByIdOutput(CreateReportOutput report);

    ReportEntity updateReportInputToReportEntity(UpdateReportInput reportDto);

    UpdateReportversionInput updateReportInputToUpdateReportversionInput(UpdateReportInput reportDto);

    @Mappings(
        {
            @Mapping(source = "entity.id", target = "id"),
            @Mapping(source = "entity.users.id", target = "ownerId"),
            @Mapping(source = "entity.users.username", target = "ownerDescriptiveField"),
        }
    )
    UpdateReportOutput reportEntityAndUpdateReportversionOutputToUpdateReportOutput(
        ReportEntity entity,
        UpdateReportversionOutput reportversion
    );

    @Mappings(
        {
            @Mapping(source = "reportversion.userId", target = "userId"),
            @Mapping(source = "report.users.id", target = "ownerId"),
            @Mapping(source = "reportversion.reportId", target = "id"),
            @Mapping(source = "report.versiono", target = "versiono"),
        }
    )
    FindReportByIdOutput reportEntitiesToFindReportByIdOutput(ReportEntity report, ReportversionEntity reportversion);

    @Mappings(
        {
            @Mapping(source = "entity.users.id", target = "ownerId"),
            @Mapping(source = "entity.versiono", target = "versiono"),
        }
    )
    FindReportByIdOutput reportEntityToFindReportByIdOutput(ReportEntity entity, ReportversionEntity reportversion);

    @Mappings({ @Mapping(source = "users.id", target = "id"), @Mapping(source = "report.id", target = "reportId") })
    GetUsersOutput usersEntityToGetUsersOutput(UsersEntity users, ReportEntity report);

    @Mappings(
        {
            @Mapping(source = "reportversion.userId", target = "userId"),
            @Mapping(source = "reportversion.reportId", target = "id"),
        }
    )
    ReportDetailsOutput reportEntitiesToReportDetailsOutput(ReportEntity report, ReportversionEntity reportversion);
}
