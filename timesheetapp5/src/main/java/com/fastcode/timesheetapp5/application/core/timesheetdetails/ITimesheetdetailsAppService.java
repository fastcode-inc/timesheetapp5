package com.fastcode.timesheetapp5.application.core.timesheetdetails;

import com.fastcode.timesheetapp5.application.core.timesheetdetails.dto.*;
import com.fastcode.timesheetapp5.commons.search.SearchCriteria;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface ITimesheetdetailsAppService {
    //CRUD Operations

    CreateTimesheetdetailsOutput create(CreateTimesheetdetailsInput timesheetdetails);

    void delete(Long id);

    UpdateTimesheetdetailsOutput update(Long id, UpdateTimesheetdetailsInput input);

    FindTimesheetdetailsByIdOutput findById(Long id);

    List<FindTimesheetdetailsByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    //Relationship Operations
    //Relationship Operations

    GetTaskOutput getTask(Long timesheetdetailsid);

    GetTimesheetOutput getTimesheet(Long timesheetdetailsid);
    //Join Column Parsers
}
