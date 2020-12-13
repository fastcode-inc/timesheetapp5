package com.fastcode.timesheetapp5.addons.reporting.application.reportversion;

import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.CreateReportversionInput;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.CreateReportversionOutput;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.FindReportversionByIdOutput;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.GetUsersOutput;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.UpdateReportversionInput;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.UpdateReportversionOutput;
import com.fastcode.timesheetapp5.addons.reporting.domain.reportversion.ReportversionEntity;
import com.fastcode.timesheetapp5.domain.core.authorization.users.UsersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IReportversionMapper {
    ReportversionEntity createReportversionInputToReportversionEntity(CreateReportversionInput reportversionDto);

    @Mappings(
        {
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "users.username", target = "userDescriptiveField"),
            @Mapping(source = "report.id", target = "reportId"),
        }
    )
    CreateReportversionOutput reportversionEntityToCreateReportversionOutput(ReportversionEntity entity);

    @Mappings(
        { @Mapping(source = "userId", target = "userId"), @Mapping(source = "version", target = "reportVersion") }
    )
    ReportversionEntity reportversionEntityToReportversionEntity(
        ReportversionEntity entity,
        Long userId,
        String version
    );

    ReportversionEntity updateReportversionInputToReportversionEntity(UpdateReportversionInput reportversionDto);

    @Mappings(
        {
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "users.username", target = "userDescriptiveField"),
            @Mapping(source = "report.id", target = "reportId"),
        }
    )
    UpdateReportversionOutput reportversionEntityToUpdateReportversionOutput(ReportversionEntity entity);

    @Mappings(
        {
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "users.username", target = "userDescriptiveField"),
            @Mapping(source = "report.id", target = "reportId"),
        }
    )
    FindReportversionByIdOutput reportversionEntityToFindReportversionByIdOutput(ReportversionEntity entity);

    @Mappings(
        {
            @Mapping(source = "users.id", target = "id"),
            @Mapping(source = "reportversion.reportVersion", target = "reportVersion"),
        }
    )
    GetUsersOutput usersEntityToGetUsersOutput(UsersEntity users, ReportversionEntity reportversion);
}
