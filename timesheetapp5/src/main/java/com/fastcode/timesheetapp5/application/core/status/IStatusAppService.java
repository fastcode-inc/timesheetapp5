package com.fastcode.timesheetapp5.application.core.status;

import com.fastcode.timesheetapp5.application.core.status.dto.*;
import com.fastcode.timesheetapp5.commons.search.SearchCriteria;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface IStatusAppService {
    //CRUD Operations

    CreateStatusOutput create(CreateStatusInput status);

    void delete(Long id);

    UpdateStatusOutput update(Long id, UpdateStatusInput input);

    FindStatusByIdOutput findById(Long id);

    List<FindStatusByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;

    //Join Column Parsers

    Map<String, String> parseTimesheetstatusJoinColumn(String keysString);
}
