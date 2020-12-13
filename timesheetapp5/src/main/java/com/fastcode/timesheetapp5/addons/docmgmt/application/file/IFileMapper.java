package com.fastcode.timesheetapp5.addons.docmgmt.application.file;

import com.fastcode.timesheetapp5.addons.docmgmt.application.file.dto.*;
import com.fastcode.timesheetapp5.addons.docmgmt.domain.file.FileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IFileMapper {
    @Mappings({ @Mapping(target = "versionp", source = "fileDto.versiono") })
    FileEntity createFileInputToFileEntity(CreateFileInput fileDto);

    @Mappings({ @Mapping(target = "versiono", source = "entity.versionp") })
    CreateFileOutput fileEntityToCreateFileOutput(FileEntity entity);

    @Mappings({ @Mapping(target = "versionp", source = "fileDto.versiono") })
    FileEntity updateFileInputToFileEntity(UpdateFileInput fileDto);

    @Mappings({ @Mapping(target = "versiono", source = "entity.versionp") })
    UpdateFileOutput fileEntityToUpdateFileOutput(FileEntity entity);

    @Mappings({ @Mapping(target = "versiono", source = "entity.versionp") })
    FindFileByIdOutput fileEntityToFindFileByIdOutput(FileEntity entity);
}
