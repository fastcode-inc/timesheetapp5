package com.fastcode.timesheetapp5.application.core.timesheetstatus;

import com.fastcode.timesheetapp5.application.core.timesheetstatus.dto.*;
import com.fastcode.timesheetapp5.domain.core.status.StatusEntity;
import com.fastcode.timesheetapp5.domain.core.timesheet.TimesheetEntity;
import com.fastcode.timesheetapp5.domain.core.timesheetstatus.TimesheetstatusEntity;
import java.time.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ITimesheetstatusMapper {
    TimesheetstatusEntity createTimesheetstatusInputToTimesheetstatusEntity(
        CreateTimesheetstatusInput timesheetstatusDto
    );

    @Mappings(
        {
            @Mapping(source = "entity.status.id", target = "statusDescriptiveField"),
            @Mapping(source = "entity.timesheet.id", target = "timesheetDescriptiveField"),
        }
    )
    CreateTimesheetstatusOutput timesheetstatusEntityToCreateTimesheetstatusOutput(TimesheetstatusEntity entity);

    TimesheetstatusEntity updateTimesheetstatusInputToTimesheetstatusEntity(
        UpdateTimesheetstatusInput timesheetstatusDto
    );

    @Mappings(
        {
            @Mapping(source = "entity.status.id", target = "statusDescriptiveField"),
            @Mapping(source = "entity.timesheet.id", target = "timesheetDescriptiveField"),
        }
    )
    UpdateTimesheetstatusOutput timesheetstatusEntityToUpdateTimesheetstatusOutput(TimesheetstatusEntity entity);

    @Mappings(
        {
            @Mapping(source = "entity.status.id", target = "statusDescriptiveField"),
            @Mapping(source = "entity.timesheet.id", target = "timesheetDescriptiveField"),
        }
    )
    FindTimesheetstatusByIdOutput timesheetstatusEntityToFindTimesheetstatusByIdOutput(TimesheetstatusEntity entity);

    @Mappings(
        {
            @Mapping(source = "foundTimesheetstatus.statusid", target = "timesheetstatusStatusid"),
            @Mapping(source = "foundTimesheetstatus.timesheetid", target = "timesheetstatusTimesheetid"),
        }
    )
    GetStatusOutput statusEntityToGetStatusOutput(StatusEntity status, TimesheetstatusEntity foundTimesheetstatus);

    @Mappings(
        {
            @Mapping(source = "timesheet.notes", target = "notes"),
            @Mapping(source = "foundTimesheetstatus.statusid", target = "timesheetstatusStatusid"),
            @Mapping(source = "foundTimesheetstatus.timesheetid", target = "timesheetstatusTimesheetid"),
        }
    )
    GetTimesheetOutput timesheetEntityToGetTimesheetOutput(
        TimesheetEntity timesheet,
        TimesheetstatusEntity foundTimesheetstatus
    );
}
