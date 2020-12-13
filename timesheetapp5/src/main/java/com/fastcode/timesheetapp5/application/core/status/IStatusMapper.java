package com.fastcode.timesheetapp5.application.core.status;

import com.fastcode.timesheetapp5.application.core.status.dto.*;
import com.fastcode.timesheetapp5.domain.core.status.StatusEntity;
import java.time.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IStatusMapper {
    StatusEntity createStatusInputToStatusEntity(CreateStatusInput statusDto);

    CreateStatusOutput statusEntityToCreateStatusOutput(StatusEntity entity);

    StatusEntity updateStatusInputToStatusEntity(UpdateStatusInput statusDto);

    UpdateStatusOutput statusEntityToUpdateStatusOutput(StatusEntity entity);

    FindStatusByIdOutput statusEntityToFindStatusByIdOutput(StatusEntity entity);
}
