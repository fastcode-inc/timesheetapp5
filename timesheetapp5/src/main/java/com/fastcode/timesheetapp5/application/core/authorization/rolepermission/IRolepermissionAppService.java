package com.fastcode.timesheetapp5.application.core.authorization.rolepermission;

import com.fastcode.timesheetapp5.application.core.authorization.rolepermission.dto.*;
import com.fastcode.timesheetapp5.commons.search.SearchCriteria;
import com.fastcode.timesheetapp5.domain.core.authorization.rolepermission.RolepermissionId;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface IRolepermissionAppService {
    //CRUD Operations

    CreateRolepermissionOutput create(CreateRolepermissionInput rolepermission);

    void delete(RolepermissionId rolepermissionId);

    UpdateRolepermissionOutput update(RolepermissionId rolepermissionId, UpdateRolepermissionInput input);

    FindRolepermissionByIdOutput findById(RolepermissionId rolepermissionId);

    List<FindRolepermissionByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;

    void deleteUserTokens(Long roleId);
    //Relationship Operations
    //Relationship Operations

    GetPermissionOutput getPermission(RolepermissionId rolepermissionId);

    GetRoleOutput getRole(RolepermissionId rolepermissionId);

    //Join Column Parsers

    RolepermissionId parseRolepermissionKey(String keysString);
}
