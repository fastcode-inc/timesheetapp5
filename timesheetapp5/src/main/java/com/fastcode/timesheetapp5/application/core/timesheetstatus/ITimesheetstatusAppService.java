package com.fastcode.timesheetapp5.application.core.timesheetstatus;

import com.fastcode.timesheetapp5.application.core.timesheetstatus.dto.*;
import com.fastcode.timesheetapp5.commons.search.SearchCriteria;
import com.fastcode.timesheetapp5.domain.core.timesheetstatus.TimesheetstatusId;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface ITimesheetstatusAppService {
    //CRUD Operations

    CreateTimesheetstatusOutput create(CreateTimesheetstatusInput timesheetstatus);

    void delete(TimesheetstatusId timesheetstatusId);

    UpdateTimesheetstatusOutput update(TimesheetstatusId timesheetstatusId, UpdateTimesheetstatusInput input);

    FindTimesheetstatusByIdOutput findById(TimesheetstatusId timesheetstatusId);

    List<FindTimesheetstatusByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    //Relationship Operations
    //Relationship Operations

    GetStatusOutput getStatus(TimesheetstatusId timesheetstatusId);

    GetTimesheetOutput getTimesheet(TimesheetstatusId timesheetstatusId);

    //Join Column Parsers

    TimesheetstatusId parseTimesheetstatusKey(String keysString);
}
