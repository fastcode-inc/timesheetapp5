package com.fastcode.timesheetapp5.application.core.timesheet;

import com.fastcode.timesheetapp5.application.core.timesheet.dto.*;
import com.fastcode.timesheetapp5.domain.core.authorization.users.UsersEntity;
import com.fastcode.timesheetapp5.domain.core.timesheet.TimesheetEntity;
import java.time.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ITimesheetMapper {
    TimesheetEntity createTimesheetInputToTimesheetEntity(CreateTimesheetInput timesheetDto);

    @Mappings(
        {
            @Mapping(source = "entity.users.id", target = "userid"),
            @Mapping(source = "entity.users.id", target = "usersDescriptiveField"),
        }
    )
    CreateTimesheetOutput timesheetEntityToCreateTimesheetOutput(TimesheetEntity entity);

    TimesheetEntity updateTimesheetInputToTimesheetEntity(UpdateTimesheetInput timesheetDto);

    @Mappings(
        {
            @Mapping(source = "entity.users.id", target = "userid"),
            @Mapping(source = "entity.users.id", target = "usersDescriptiveField"),
        }
    )
    UpdateTimesheetOutput timesheetEntityToUpdateTimesheetOutput(TimesheetEntity entity);

    @Mappings(
        {
            @Mapping(source = "entity.users.id", target = "userid"),
            @Mapping(source = "entity.users.id", target = "usersDescriptiveField"),
        }
    )
    FindTimesheetByIdOutput timesheetEntityToFindTimesheetByIdOutput(TimesheetEntity entity);

    @Mappings(
        { @Mapping(source = "users.id", target = "id"), @Mapping(source = "foundTimesheet.id", target = "timesheetId") }
    )
    GetUsersOutput usersEntityToGetUsersOutput(UsersEntity users, TimesheetEntity foundTimesheet);
}
