package com.fastcode.timesheetapp5.restcontrollers.core;

import com.fastcode.timesheetapp5.application.core.authorization.users.IUsersAppService;
import com.fastcode.timesheetapp5.application.core.task.ITaskAppService;
import com.fastcode.timesheetapp5.application.core.usertask.IUsertaskAppService;
import com.fastcode.timesheetapp5.application.core.usertask.dto.*;
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
@RequestMapping("/usertask")
@RequiredArgsConstructor
public class UsertaskController {

    @Qualifier("usertaskAppService")
    @NonNull
    protected final IUsertaskAppService _usertaskAppService;

    @Qualifier("taskAppService")
    @NonNull
    protected final ITaskAppService _taskAppService;

    @Qualifier("usersAppService")
    @NonNull
    protected final IUsersAppService _usersAppService;

    @NonNull
    protected final LoggingHelper logHelper;

    @NonNull
    protected final Environment env;

    @PreAuthorize("hasAnyAuthority('USERTASKENTITY_CREATE')")
    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    public ResponseEntity<CreateUsertaskOutput> create(@RequestBody @Valid CreateUsertaskInput usertask) {
        CreateUsertaskOutput output = _usertaskAppService.create(usertask);
        return new ResponseEntity(output, HttpStatus.OK);
    }

    // ------------ Delete usertask ------------
    @PreAuthorize("hasAnyAuthority('USERTASKENTITY_DELETE')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = { "application/json" })
    public void delete(@PathVariable String id) {
        FindUsertaskByIdOutput output = _usertaskAppService.findById(Long.valueOf(id));
        Optional
            .ofNullable(output)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("There does not exist a usertask with a id=%s", id))
            );

        _usertaskAppService.delete(Long.valueOf(id));
    }

    // ------------ Update usertask ------------
    @PreAuthorize("hasAnyAuthority('USERTASKENTITY_UPDATE')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<UpdateUsertaskOutput> update(
        @PathVariable String id,
        @RequestBody @Valid UpdateUsertaskInput usertask
    ) {
        FindUsertaskByIdOutput currentUsertask = _usertaskAppService.findById(Long.valueOf(id));
        Optional
            .ofNullable(currentUsertask)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("Unable to update. Usertask with id=%s not found.", id))
            );

        usertask.setVersiono(currentUsertask.getVersiono());
        UpdateUsertaskOutput output = _usertaskAppService.update(Long.valueOf(id), usertask);
        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USERTASKENTITY_READ')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<FindUsertaskByIdOutput> findById(@PathVariable String id) {
        FindUsertaskByIdOutput output = _usertaskAppService.findById(Long.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USERTASKENTITY_READ')")
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

        return ResponseEntity.ok(_usertaskAppService.find(searchCriteria, Pageable));
    }

    @PreAuthorize("hasAnyAuthority('USERTASKENTITY_READ')")
    @RequestMapping(
        value = "/{id}/task",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetTaskOutput> getTask(@PathVariable String id) {
        GetTaskOutput output = _usertaskAppService.getTask(Long.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USERTASKENTITY_READ')")
    @RequestMapping(
        value = "/{id}/users",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetUsersOutput> getUsers(@PathVariable String id) {
        GetUsersOutput output = _usertaskAppService.getUsers(Long.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }
}
