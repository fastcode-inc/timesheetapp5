package com.fastcode.timesheetapp5.application.core.timesheet;

import com.fastcode.timesheetapp5.application.core.timesheet.dto.*;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.commons.search.*;
import com.fastcode.timesheetapp5.domain.core.authorization.users.IUsersRepository;
import com.fastcode.timesheetapp5.domain.core.authorization.users.UsersEntity;
import com.fastcode.timesheetapp5.domain.core.timesheet.ITimesheetRepository;
import com.fastcode.timesheetapp5.domain.core.timesheet.QTimesheetEntity;
import com.fastcode.timesheetapp5.domain.core.timesheet.TimesheetEntity;
import com.querydsl.core.BooleanBuilder;
import java.time.*;
import java.util.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("timesheetAppService")
@RequiredArgsConstructor
public class TimesheetAppService implements ITimesheetAppService {

    @Qualifier("timesheetRepository")
    @NonNull
    protected final ITimesheetRepository _timesheetRepository;

    @Qualifier("usersRepository")
    @NonNull
    protected final IUsersRepository _usersRepository;

    @Qualifier("ITimesheetMapperImpl")
    @NonNull
    protected final ITimesheetMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateTimesheetOutput create(CreateTimesheetInput input) {
        TimesheetEntity timesheet = mapper.createTimesheetInputToTimesheetEntity(input);
        UsersEntity foundUsers = null;
        if (input.getUserid() != null) {
            foundUsers = _usersRepository.findById(input.getUserid()).orElse(null);

            if (foundUsers != null) {
                timesheet.setUsers(foundUsers);
            }
        }

        TimesheetEntity createdTimesheet = _timesheetRepository.save(timesheet);
        return mapper.timesheetEntityToCreateTimesheetOutput(createdTimesheet);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateTimesheetOutput update(Long timesheetId, UpdateTimesheetInput input) {
        TimesheetEntity timesheet = mapper.updateTimesheetInputToTimesheetEntity(input);
        UsersEntity foundUsers = null;

        if (input.getUserid() != null) {
            foundUsers = _usersRepository.findById(input.getUserid()).orElse(null);

            if (foundUsers != null) {
                timesheet.setUsers(foundUsers);
            }
        }

        TimesheetEntity updatedTimesheet = _timesheetRepository.save(timesheet);
        return mapper.timesheetEntityToUpdateTimesheetOutput(updatedTimesheet);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long timesheetId) {
        TimesheetEntity existing = _timesheetRepository.findById(timesheetId).orElse(null);
        _timesheetRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindTimesheetByIdOutput findById(Long timesheetId) {
        TimesheetEntity foundTimesheet = _timesheetRepository.findById(timesheetId).orElse(null);
        if (foundTimesheet == null) return null;

        return mapper.timesheetEntityToFindTimesheetByIdOutput(foundTimesheet);
    }

    //Users
    // ReST API Call - GET /timesheet/1/users
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetUsersOutput getUsers(Long timesheetId) {
        TimesheetEntity foundTimesheet = _timesheetRepository.findById(timesheetId).orElse(null);
        if (foundTimesheet == null) {
            logHelper.getLogger().error("There does not exist a timesheet wth a id=%s", timesheetId);
            return null;
        }
        UsersEntity re = foundTimesheet.getUsers();
        return mapper.usersEntityToGetUsersOutput(re, foundTimesheet);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindTimesheetByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<TimesheetEntity> foundTimesheet = _timesheetRepository.findAll(search(search), pageable);
        List<TimesheetEntity> timesheetList = foundTimesheet.getContent();
        Iterator<TimesheetEntity> timesheetIterator = timesheetList.iterator();
        List<FindTimesheetByIdOutput> output = new ArrayList<>();

        while (timesheetIterator.hasNext()) {
            TimesheetEntity timesheet = timesheetIterator.next();
            output.add(mapper.timesheetEntityToFindTimesheetByIdOutput(timesheet));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QTimesheetEntity timesheet = QTimesheetEntity.timesheetEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(timesheet, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("userid") ||
                    list.get(i).replace("%20", "").trim().equals("id") ||
                    list.get(i).replace("%20", "").trim().equals("notes") ||
                    list.get(i).replace("%20", "").trim().equals("periodendingdate")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QTimesheetEntity timesheet,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("id")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(timesheet.id.eq(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(timesheet.id.ne(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        timesheet.id.between(
                            Long.valueOf(details.getValue().getStartingValue()),
                            Long.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        timesheet.id.goe(Long.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        timesheet.id.loe(Long.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("notes")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    timesheet.notes.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    timesheet.notes.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    timesheet.notes.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("periodendingdate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    timesheet.periodendingdate.eq(
                        SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())
                    )
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    timesheet.periodendingdate.ne(
                        SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())
                    )
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        timesheet.periodendingdate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(
                        timesheet.periodendingdate.loe(endLocalDateTime)
                    ); else if (startLocalDateTime != null) builder.and(
                        timesheet.periodendingdate.goe(startLocalDateTime)
                    );
                }
            }

            if (details.getKey().replace("%20", "").trim().equals("users")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(timesheet.users.id.eq(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(timesheet.users.id.ne(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        timesheet.users.id.between(
                            Long.valueOf(details.getValue().getStartingValue()),
                            Long.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        timesheet.users.id.goe(Long.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        timesheet.users.id.loe(Long.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("userid")) {
                builder.and(timesheet.users.id.eq(Long.parseLong(joinCol.getValue())));
            }

            if (joinCol != null && joinCol.getKey().equals("users")) {
                builder.and(timesheet.users.id.eq(Long.parseLong(joinCol.getValue())));
            }
        }
        return builder;
    }

    public Map<String, String> parseTimesheetdetailsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("timesheetid", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseTimesheetstatusJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("timesheetid", keysString);

        return joinColumnMap;
    }
}
