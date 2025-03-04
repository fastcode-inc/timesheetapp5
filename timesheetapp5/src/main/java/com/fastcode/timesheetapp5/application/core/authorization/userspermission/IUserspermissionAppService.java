package com.fastcode.timesheetapp5.application.core.authorization.userspermission;

import com.fastcode.timesheetapp5.application.core.authorization.userspermission.dto.*;
import com.fastcode.timesheetapp5.commons.search.SearchCriteria;
import com.fastcode.timesheetapp5.domain.core.authorization.userspermission.UserspermissionId;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface IUserspermissionAppService {
    //CRUD Operations

    CreateUserspermissionOutput create(CreateUserspermissionInput userspermission);

    void delete(UserspermissionId userspermissionId);

    UpdateUserspermissionOutput update(UserspermissionId userspermissionId, UpdateUserspermissionInput input);

    FindUserspermissionByIdOutput findById(UserspermissionId userspermissionId);

    List<FindUserspermissionByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    //Relationship Operations
    //Relationship Operations

    GetPermissionOutput getPermission(UserspermissionId userspermissionId);

    GetUsersOutput getUsers(UserspermissionId userspermissionId);

    //Join Column Parsers

    UserspermissionId parseUserspermissionKey(String keysString);
}
