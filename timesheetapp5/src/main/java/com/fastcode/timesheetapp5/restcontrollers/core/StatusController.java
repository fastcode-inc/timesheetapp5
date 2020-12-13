package com.fastcode.timesheetapp5.restcontrollers.core;

import com.fastcode.timesheetapp5.application.core.status.IStatusAppService;
import com.fastcode.timesheetapp5.application.core.status.dto.*;
import com.fastcode.timesheetapp5.application.core.timesheetstatus.ITimesheetstatusAppService;
import com.fastcode.timesheetapp5.application.core.timesheetstatus.dto.FindTimesheetstatusByIdOutput;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.commons.search.OffsetBasedPageRequest;
import com.fastcode.timesheetapp5.commons.search.SearchCriteria;
import com.fastcode.timesheetapp5.commons.search.SearchUtils;
import java.time.*;
import java.util.*;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusController {

    @Qualifier("statusAppService")
    @NonNull
    protected final IStatusAppService _statusAppService;

    @Qualifier("timesheetstatusAppService")
    @NonNull
    protected final ITimesheetstatusAppService _timesheetstatusAppService;

    @NonNull
    protected final LoggingHelper logHelper;

    @NonNull
    protected final Environment env;

    @PreAuthorize("hasAnyAuthority('STATUSENTITY_CREATE')")
    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    public ResponseEntity<CreateStatusOutput> create(@RequestBody @Valid CreateStatusInput status) {
        CreateStatusOutput output = _statusAppService.create(status);
        return new ResponseEntity(output, HttpStatus.OK);
    }

    // ------------ Delete status ------------
    @PreAuthorize("hasAnyAuthority('STATUSENTITY_DELETE')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = { "application/json" })
    public void delete(@PathVariable String id) {
        FindStatusByIdOutput output = _statusAppService.findById(Long.valueOf(id));
        Optional
            .ofNullable(output)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("There does not exist a status with a id=%s", id))
            );

        _statusAppService.delete(Long.valueOf(id));
    }

    // ------------ Update status ------------
    @PreAuthorize("hasAnyAuthority('STATUSENTITY_UPDATE')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<UpdateStatusOutput> update(
        @PathVariable String id,
        @RequestBody @Valid UpdateStatusInput status
    ) {
        FindStatusByIdOutput currentStatus = _statusAppService.findById(Long.valueOf(id));
        Optional
            .ofNullable(currentStatus)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("Unable to update. Status with id=%s not found.", id))
            );

        status.setVersiono(currentStatus.getVersiono());
        UpdateStatusOutput output = _statusAppService.update(Long.valueOf(id), status);
        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('STATUSENTITY_READ')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<FindStatusByIdOutput> findById(@PathVariable String id) {
        FindStatusByIdOutput output = _statusAppService.findById(Long.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('STATUSENTITY_READ')")
    @RequestMapping(method = RequestMethod.GET, consumes = { "application/json" }, produces = { "application/json" })
    public ResponseEntity find(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "offset", required = false) String offset,
        @RequestParam(value = "limit", required = false) String limit,
        Sort sort
    ) throws Exception {
        if (offset == null) {
            offset = env.getProperty("fastCode.offset.default");
        }
        if (limit == null) {
            limit = env.getProperty("fastCode.limit.default");
        }

        if (sort == null || sort.isEmpty()) {
            sort = Sort.by(Sort.Direction.ASC, "id");
        }

        Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
        SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

        return ResponseEntity.ok(_statusAppService.find(searchCriteria, Pageable));
    }

    @PreAuthorize("hasAnyAuthority('STATUSENTITY_READ')")
    @RequestMapping(
        value = "/{id}/timesheetstatus",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity getTimesheetstatus(
        @PathVariable String id,
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "offset", required = false) String offset,
        @RequestParam(value = "limit", required = false) String limit,
        Sort sort
    ) throws Exception {
        if (offset == null) {
            offset = env.getProperty("fastCode.offset.default");
        }
        if (limit == null) {
            limit = env.getProperty("fastCode.limit.default");
        }

        if (sort == null || sort.isEmpty()) {
            sort = Sort.by(Sort.Direction.ASC, "statusid");
        }

        Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

        SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
        Map<String, String> joinColDetails = _statusAppService.parseTimesheetstatusJoinColumn(id);
        Optional
            .ofNullable(joinColDetails)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid join column")));

        searchCriteria.setJoinColumns(joinColDetails);

        List<FindTimesheetstatusByIdOutput> output = _timesheetstatusAppService.find(searchCriteria, pageable);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }
}
