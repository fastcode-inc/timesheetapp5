package com.fastcode.timesheetapp5.application.core.timesheet;

import com.fastcode.timesheetapp5.application.core.timesheet.dto.*;
import com.fastcode.timesheetapp5.commons.search.SearchCriteria;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface ITimesheetAppService {
    //CRUD Operations

    CreateTimesheetOutput create(CreateTimesheetInput timesheet);

    void delete(Long id);

    UpdateTimesheetOutput update(Long id, UpdateTimesheetInput input);

    FindTimesheetByIdOutput findById(Long id);

    List<FindTimesheetByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    //Relationship Operations

    GetUsersOutput getUsers(Long timesheetid);

    //Join Column Parsers

    Map<String, String> parseTimesheetdetailsJoinColumn(String keysString);

    Map<String, String> parseTimesheetstatusJoinColumn(String keysString);
}
