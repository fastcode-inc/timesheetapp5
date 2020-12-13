package com.fastcode.timesheetapp5.restcontrollers.core;

import com.fastcode.timesheetapp5.application.core.status.IStatusAppService;
import com.fastcode.timesheetapp5.application.core.timesheet.ITimesheetAppService;
import com.fastcode.timesheetapp5.application.core.timesheetstatus.ITimesheetstatusAppService;
import com.fastcode.timesheetapp5.application.core.timesheetstatus.dto.*;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.commons.search.OffsetBasedPageRequest;
import com.fastcode.timesheetapp5.commons.search.SearchCriteria;
import com.fastcode.timesheetapp5.commons.search.SearchUtils;
import com.fastcode.timesheetapp5.domain.core.timesheetstatus.TimesheetstatusId;
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
@RequestMapping("/timesheetstatus")
@RequiredArgsConstructor
public class TimesheetstatusController {

    @Qualifier("timesheetstatusAppService")
    @NonNull
    protected final ITimesheetstatusAppService _timesheetstatusAppService;

    @Qualifier("statusAppService")
    @NonNull
    protected final IStatusAppService _statusAppService;

    @Qualifier("timesheetAppService")
    @NonNull
    protected final ITimesheetAppService _timesheetAppService;

    @NonNull
    protected final LoggingHelper logHelper;

    @NonNull
    protected final Environment env;

    @PreAuthorize("hasAnyAuthority('TIMESHEETSTATUSENTITY_CREATE')")
    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    public ResponseEntity<CreateTimesheetstatusOutput> create(
        @RequestBody @Valid CreateTimesheetstatusInput timesheetstatus
    ) {
        CreateTimesheetstatusOutput output = _timesheetstatusAppService.create(timesheetstatus);
        return new ResponseEntity(output, HttpStatus.OK);
    }

    // ------------ Delete timesheetstatus ------------
    @PreAuthorize("hasAnyAuthority('TIMESHEETSTATUSENTITY_DELETE')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = { "application/json" })
    public void delete(@PathVariable String id) {
        TimesheetstatusId timesheetstatusid = _timesheetstatusAppService.parseTimesheetstatusKey(id);
        Optional
            .ofNullable(timesheetstatusid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        FindTimesheetstatusByIdOutput output = _timesheetstatusAppService.findById(timesheetstatusid);
        Optional
            .ofNullable(output)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("There does not exist a timesheetstatus with a id=%s", id)
                    )
            );

        _timesheetstatusAppService.delete(timesheetstatusid);
    }

    // ------------ Update timesheetstatus ------------
    @PreAuthorize("hasAnyAuthority('TIMESHEETSTATUSENTITY_UPDATE')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<UpdateTimesheetstatusOutput> update(
        @PathVariable String id,
        @RequestBody @Valid UpdateTimesheetstatusInput timesheetstatus
    ) {
        TimesheetstatusId timesheetstatusid = _timesheetstatusAppService.parseTimesheetstatusKey(id);

        Optional
            .ofNullable(timesheetstatusid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        FindTimesheetstatusByIdOutput currentTimesheetstatus = _timesheetstatusAppService.findById(timesheetstatusid);
        Optional
            .ofNullable(currentTimesheetstatus)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("Unable to update. Timesheetstatus with id=%s not found.", id)
                    )
            );

        timesheetstatus.setVersiono(currentTimesheetstatus.getVersiono());
        UpdateTimesheetstatusOutput output = _timesheetstatusAppService.update(timesheetstatusid, timesheetstatus);
        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TIMESHEETSTATUSENTITY_READ')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<FindTimesheetstatusByIdOutput> findById(@PathVariable String id) {
        TimesheetstatusId timesheetstatusid = _timesheetstatusAppService.parseTimesheetstatusKey(id);
        Optional
            .ofNullable(timesheetstatusid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        FindTimesheetstatusByIdOutput output = _timesheetstatusAppService.findById(timesheetstatusid);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TIMESHEETSTATUSENTITY_READ')")
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
            sort = Sort.by(Sort.Direction.ASC, "statusid");
        }

        Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
        SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

        return ResponseEntity.ok(_timesheetstatusAppService.find(searchCriteria, Pageable));
    }

    @PreAuthorize("hasAnyAuthority('TIMESHEETSTATUSENTITY_READ')")
    @RequestMapping(
        value = "/{id}/status",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetStatusOutput> getStatus(@PathVariable String id) {
        TimesheetstatusId timesheetstatusid = _timesheetstatusAppService.parseTimesheetstatusKey(id);
        Optional
            .ofNullable(timesheetstatusid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        GetStatusOutput output = _timesheetstatusAppService.getStatus(timesheetstatusid);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TIMESHEETSTATUSENTITY_READ')")
    @RequestMapping(
        value = "/{id}/timesheet",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetTimesheetOutput> getTimesheet(@PathVariable String id) {
        TimesheetstatusId timesheetstatusid = _timesheetstatusAppService.parseTimesheetstatusKey(id);
        Optional
            .ofNullable(timesheetstatusid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        GetTimesheetOutput output = _timesheetstatusAppService.getTimesheet(timesheetstatusid);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }
}
