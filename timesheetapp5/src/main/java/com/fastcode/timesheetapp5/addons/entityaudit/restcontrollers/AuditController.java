package com.fastcode.timesheetapp5.addons.entityaudit.restcontrollers;

import com.fastcode.timesheetapp5.commons.search.SearchUtils;
import com.fastcode.timesheetapp5.domain.core.authorization.permission.PermissionEntity;
import com.fastcode.timesheetapp5.domain.core.authorization.role.RoleEntity;
import com.fastcode.timesheetapp5.domain.core.authorization.rolepermission.RolepermissionEntity;
import com.fastcode.timesheetapp5.domain.core.authorization.userspermission.UserspermissionEntity;
import com.fastcode.timesheetapp5.domain.core.authorization.usersrole.UsersroleEntity;
import com.fastcode.timesheetapp5.domain.core.customer.CustomerEntity;
import com.fastcode.timesheetapp5.domain.core.project.ProjectEntity;
import com.fastcode.timesheetapp5.domain.core.status.StatusEntity;
import com.fastcode.timesheetapp5.domain.core.task.TaskEntity;
import com.fastcode.timesheetapp5.domain.core.timesheet.TimesheetEntity;
import com.fastcode.timesheetapp5.domain.core.timesheetdetails.TimesheetdetailsEntity;
import com.fastcode.timesheetapp5.domain.core.timesheetstatus.TimesheetstatusEntity;
import com.fastcode.timesheetapp5.domain.core.usertask.UsertaskEntity;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasAnyAuthority('ENTITYHISTORY')")
@RestController
@RequestMapping(value = "/audit")
public class AuditController {

    @Autowired
    private Environment env;

    private final Javers javers;

    @Autowired
    public AuditController(Javers javers) {
        this.javers = javers;
    }

    @RequestMapping(
        value = "/role",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public String getRoleChanges(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "offset", required = false) String offset,
        @RequestParam(value = "limit", required = false) String limit
    ) {
        QueryBuilder jqlQuery = addPaginationAndFilters(QueryBuilder.byClass(RoleEntity.class), limit, offset, search);
        List<Change> changes = javers.findChanges(jqlQuery.withNewObjectChanges().build());
        return javers.getJsonConverter().toJson(changes);
    }

    @RequestMapping(
        value = "/permission",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public String getPermissionChanges(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "offset", required = false) String offset,
        @RequestParam(value = "limit", required = false) String limit
    ) {
        QueryBuilder jqlQuery = addPaginationAndFilters(
            QueryBuilder.byClass(PermissionEntity.class),
            limit,
            offset,
            search
        );
        List<Change> changes = javers.findChanges(jqlQuery.withNewObjectChanges().build());
        return javers.getJsonConverter().toJson(changes);
    }

    @RequestMapping(
        value = "/rolepermission",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public String getRolepermissionChanges(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "offset", required = false) String offset,
        @RequestParam(value = "limit", required = false) String limit
    ) {
        QueryBuilder jqlQuery = addPaginationAndFilters(
            QueryBuilder.byClass(RolepermissionEntity.class),
            limit,
            offset,
            search
        );
        List<Change> changes = javers.findChanges(jqlQuery.withNewObjectChanges().build());
        return javers.getJsonConverter().toJson(changes);
    }

    @RequestMapping(
        value = "/userspermission",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public String getUserspermissionChanges(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "offset", required = false) String offset,
        @RequestParam(value = "limit", required = false) String limit
    ) {
        QueryBuilder jqlQuery = addPaginationAndFilters(
            QueryBuilder.byClass(UserspermissionEntity.class),
            limit,
            offset,
            search
        );
        List<Change> changes = javers.findChanges(jqlQuery.withNewObjectChanges().build());
        return javers.getJsonConverter().toJson(changes);
    }

    @RequestMapping(
        value = "/usersrole",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public String getUsersroleChanges(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "offset", required = false) String offset,
        @RequestParam(value = "limit", required = false) String limit
    ) {
        QueryBuilder jqlQuery = addPaginationAndFilters(
            QueryBuilder.byClass(UsersroleEntity.class),
            limit,
            offset,
            search
        );
        List<Change> changes = javers.findChanges(jqlQuery.withNewObjectChanges().build());
        return javers.getJsonConverter().toJson(changes);
    }

    @RequestMapping(
        value = "/status",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public String getStatusChanges(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "offset", required = false) String offset,
        @RequestParam(value = "limit", required = false) String limit
    ) {
        QueryBuilder jqlQuery = addPaginationAndFilters(
            QueryBuilder.byClass(StatusEntity.class),
            limit,
            offset,
            search
        );
        List<Change> changes = javers.findChanges(jqlQuery.withNewObjectChanges().build());
        return javers.getJsonConverter().toJson(changes);
    }

    @RequestMapping(
        value = "/task",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public String getTaskChanges(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "offset", required = false) String offset,
        @RequestParam(value = "limit", required = false) String limit
    ) {
        QueryBuilder jqlQuery = addPaginationAndFilters(QueryBuilder.byClass(TaskEntity.class), limit, offset, search);
        List<Change> changes = javers.findChanges(jqlQuery.withNewObjectChanges().build());
        return javers.getJsonConverter().toJson(changes);
    }

    @RequestMapping(
        value = "/customer",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public String getCustomerChanges(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "offset", required = false) String offset,
        @RequestParam(value = "limit", required = false) String limit
    ) {
        QueryBuilder jqlQuery = addPaginationAndFilters(
            QueryBuilder.byClass(CustomerEntity.class),
            limit,
            offset,
            search
        );
        List<Change> changes = javers.findChanges(jqlQuery.withNewObjectChanges().build());
        return javers.getJsonConverter().toJson(changes);
    }

    @RequestMapping(
        value = "/timesheet",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public String getTimesheetChanges(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "offset", required = false) String offset,
        @RequestParam(value = "limit", required = false) String limit
    ) {
        QueryBuilder jqlQuery = addPaginationAndFilters(
            QueryBuilder.byClass(TimesheetEntity.class),
            limit,
            offset,
            search
        );
        List<Change> changes = javers.findChanges(jqlQuery.withNewObjectChanges().build());
        return javers.getJsonConverter().toJson(changes);
    }

    @RequestMapping(
        value = "/project",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public String getProjectChanges(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "offset", required = false) String offset,
        @RequestParam(value = "limit", required = false) String limit
    ) {
        QueryBuilder jqlQuery = addPaginationAndFilters(
            QueryBuilder.byClass(ProjectEntity.class),
            limit,
            offset,
            search
        );
        List<Change> changes = javers.findChanges(jqlQuery.withNewObjectChanges().build());
        return javers.getJsonConverter().toJson(changes);
    }

    @RequestMapping(
        value = "/usertask",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public String getUsertaskChanges(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "offset", required = false) String offset,
        @RequestParam(value = "limit", required = false) String limit
    ) {
        QueryBuilder jqlQuery = addPaginationAndFilters(
            QueryBuilder.byClass(UsertaskEntity.class),
            limit,
            offset,
            search
        );
        List<Change> changes = javers.findChanges(jqlQuery.withNewObjectChanges().build());
        return javers.getJsonConverter().toJson(changes);
    }

    @RequestMapping(
        value = "/timesheetstatus",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public String getTimesheetstatusChanges(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "offset", required = false) String offset,
        @RequestParam(value = "limit", required = false) String limit
    ) {
        QueryBuilder jqlQuery = addPaginationAndFilters(
            QueryBuilder.byClass(TimesheetstatusEntity.class),
            limit,
            offset,
            search
        );
        List<Change> changes = javers.findChanges(jqlQuery.withNewObjectChanges().build());
        return javers.getJsonConverter().toJson(changes);
    }

    @RequestMapping(
        value = "/timesheetdetails",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public String getTimesheetdetailsChanges(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "offset", required = false) String offset,
        @RequestParam(value = "limit", required = false) String limit
    ) {
        QueryBuilder jqlQuery = addPaginationAndFilters(
            QueryBuilder.byClass(TimesheetdetailsEntity.class),
            limit,
            offset,
            search
        );
        List<Change> changes = javers.findChanges(jqlQuery.withNewObjectChanges().build());
        return javers.getJsonConverter().toJson(changes);
    }

    @RequestMapping(
        value = "/changes",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public String getAllChanges(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "offset", required = false) String offset,
        @RequestParam(value = "limit", required = false) String limit
    ) {
        JqlQuery jqlQuery = addPaginationAndFilters(
            QueryBuilder.anyDomainObject().withNewObjectChanges(),
            limit,
            offset,
            search
        )
            .build();
        List<Change> changes = javers.findChanges(jqlQuery);
        return javers.getJsonConverter().toJson(changes);
    }

    private QueryBuilder addPaginationAndFilters(QueryBuilder query, String limit, String offset, String search) {
        if (offset == null) {
            offset = env.getProperty("fastCode.offset.default");
        }
        if (limit == null) {
            limit = env.getProperty("fastCode.limit.default");
        }

        query = query.limit(Integer.parseInt(limit)).skip(Integer.parseInt(offset));
        Map<String, Object> map = parseSearchString(search);
        if (map.containsKey("author") && map.get("author") != null) {
            query =
                query
                    .byAuthor(map.get("author").toString())
                    .from((LocalDateTime) map.get("from"))
                    .to((LocalDateTime) map.get("to"));
        } else query = query.from((LocalDateTime) map.get("from")).to((LocalDateTime) map.get("to"));

        return query;
    }

    private Map<String, Object> parseSearchString(String searchString) {
        Map<String, Object> searchMap = new HashMap<>();
        if (searchString != null && searchString.length() > 0) {
            String[] fields = searchString.split(";");

            for (String field : fields) {
                String fieldName = field.substring(0, field.indexOf('=')).toLowerCase();
                String searchValue = field.substring(field.indexOf('=') + 1);

                searchMap.put(fieldName, searchValue);
            }
        }
        if (searchMap.containsKey("from")) {
            LocalDateTime from = SearchUtils
                .stringToDate(searchMap.get("from").toString())
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
            searchMap.put("from", from);
        } else {
            searchMap.put("from", LocalDateTime.of(1970, Month.JANUARY, 1, 10, 10, 30));
        }
        if (searchMap.containsKey("to")) {
            LocalDateTime to = SearchUtils
                .stringToDate(searchMap.get("to").toString())
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
            searchMap.put("to", to);
        } else {
            searchMap.put("to", LocalDateTime.now());
        }

        return searchMap;
    }
}
