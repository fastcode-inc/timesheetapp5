package com.fastcode.timesheetapp5.application.core.usertask;

import com.fastcode.timesheetapp5.application.core.usertask.dto.*;
import com.fastcode.timesheetapp5.domain.core.authorization.users.UsersEntity;
import com.fastcode.timesheetapp5.domain.core.task.TaskEntity;
import com.fastcode.timesheetapp5.domain.core.usertask.UsertaskEntity;
import java.time.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IUsertaskMapper {
    UsertaskEntity createUsertaskInputToUsertaskEntity(CreateUsertaskInput usertaskDto);

    @Mappings(
        {
            @Mapping(source = "entity.task.id", target = "taskid"),
            @Mapping(source = "entity.task.id", target = "taskDescriptiveField"),
            @Mapping(source = "entity.users.id", target = "userid"),
            @Mapping(source = "entity.users.id", target = "usersDescriptiveField"),
        }
    )
    CreateUsertaskOutput usertaskEntityToCreateUsertaskOutput(UsertaskEntity entity);

    UsertaskEntity updateUsertaskInputToUsertaskEntity(UpdateUsertaskInput usertaskDto);

    @Mappings(
        {
            @Mapping(source = "entity.task.id", target = "taskid"),
            @Mapping(source = "entity.task.id", target = "taskDescriptiveField"),
            @Mapping(source = "entity.users.id", target = "userid"),
            @Mapping(source = "entity.users.id", target = "usersDescriptiveField"),
        }
    )
    UpdateUsertaskOutput usertaskEntityToUpdateUsertaskOutput(UsertaskEntity entity);

    @Mappings(
        {
            @Mapping(source = "entity.task.id", target = "taskid"),
            @Mapping(source = "entity.task.id", target = "taskDescriptiveField"),
            @Mapping(source = "entity.users.id", target = "userid"),
            @Mapping(source = "entity.users.id", target = "usersDescriptiveField"),
        }
    )
    FindUsertaskByIdOutput usertaskEntityToFindUsertaskByIdOutput(UsertaskEntity entity);

    @Mappings(
        { @Mapping(source = "users.id", target = "id"), @Mapping(source = "foundUsertask.id", target = "usertaskId") }
    )
    GetUsersOutput usersEntityToGetUsersOutput(UsersEntity users, UsertaskEntity foundUsertask);

    @Mappings(
        { @Mapping(source = "task.id", target = "id"), @Mapping(source = "foundUsertask.id", target = "usertaskId") }
    )
    GetTaskOutput taskEntityToGetTaskOutput(TaskEntity task, UsertaskEntity foundUsertask);
}
