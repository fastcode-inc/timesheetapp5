package com.fastcode.timesheetapp5.application.core.usertask;

import com.fastcode.timesheetapp5.application.core.usertask.dto.*;
import com.fastcode.timesheetapp5.commons.search.SearchCriteria;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface IUsertaskAppService {
    //CRUD Operations

    CreateUsertaskOutput create(CreateUsertaskInput usertask);

    void delete(Long id);

    UpdateUsertaskOutput update(Long id, UpdateUsertaskInput input);

    FindUsertaskByIdOutput findById(Long id);

    List<FindUsertaskByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    //Relationship Operations
    //Relationship Operations

    GetTaskOutput getTask(Long usertaskid);

    GetUsersOutput getUsers(Long usertaskid);
    //Join Column Parsers
}
