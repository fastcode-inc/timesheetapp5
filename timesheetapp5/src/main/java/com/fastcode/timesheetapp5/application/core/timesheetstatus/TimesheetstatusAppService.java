package com.fastcode.timesheetapp5.application.core.timesheetstatus;

import com.fastcode.timesheetapp5.application.core.timesheetstatus.dto.*;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.commons.search.*;
import com.fastcode.timesheetapp5.domain.core.status.IStatusRepository;
import com.fastcode.timesheetapp5.domain.core.status.StatusEntity;
import com.fastcode.timesheetapp5.domain.core.timesheet.ITimesheetRepository;
import com.fastcode.timesheetapp5.domain.core.timesheet.TimesheetEntity;
import com.fastcode.timesheetapp5.domain.core.timesheetstatus.ITimesheetstatusRepository;
import com.fastcode.timesheetapp5.domain.core.timesheetstatus.QTimesheetstatusEntity;
import com.fastcode.timesheetapp5.domain.core.timesheetstatus.TimesheetstatusEntity;
import com.fastcode.timesheetapp5.domain.core.timesheetstatus.TimesheetstatusId;
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

@Service("timesheetstatusAppService")
@RequiredArgsConstructor
public class TimesheetstatusAppService implements ITimesheetstatusAppService {

    @Qualifier("timesheetstatusRepository")
    @NonNull
    protected final ITimesheetstatusRepository _timesheetstatusRepository;

    @Qualifier("statusRepository")
    @NonNull
    protected final IStatusRepository _statusRepository;

    @Qualifier("timesheetRepository")
    @NonNull
    protected final ITimesheetRepository _timesheetRepository;

    @Qualifier("ITimesheetstatusMapperImpl")
    @NonNull
    protected final ITimesheetstatusMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateTimesheetstatusOutput create(CreateTimesheetstatusInput input) {
        TimesheetstatusEntity timesheetstatus = mapper.createTimesheetstatusInputToTimesheetstatusEntity(input);
        StatusEntity foundStatus = null;
        TimesheetEntity foundTimesheet = null;
        if (input.getStatusid() != null) {
            foundStatus = _statusRepository.findById(input.getStatusid()).orElse(null);

            if (foundStatus != null) {
                timesheetstatus.setStatus(foundStatus);
            }
        }
        if (input.getTimesheetid() != null) {
            foundTimesheet = _timesheetRepository.findById(input.getTimesheetid()).orElse(null);

            if (foundTimesheet != null) {
                timesheetstatus.setTimesheet(foundTimesheet);
            }
        }

        TimesheetstatusEntity createdTimesheetstatus = _timesheetstatusRepository.save(timesheetstatus);
        return mapper.timesheetstatusEntityToCreateTimesheetstatusOutput(createdTimesheetstatus);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateTimesheetstatusOutput update(TimesheetstatusId timesheetstatusId, UpdateTimesheetstatusInput input) {
        TimesheetstatusEntity timesheetstatus = mapper.updateTimesheetstatusInputToTimesheetstatusEntity(input);
        StatusEntity foundStatus = null;
        TimesheetEntity foundTimesheet = null;

        if (input.getStatusid() != null) {
            foundStatus = _statusRepository.findById(input.getStatusid()).orElse(null);

            if (foundStatus != null) {
                timesheetstatus.setStatus(foundStatus);
            }
        }

        if (input.getTimesheetid() != null) {
            foundTimesheet = _timesheetRepository.findById(input.getTimesheetid()).orElse(null);

            if (foundTimesheet != null) {
                timesheetstatus.setTimesheet(foundTimesheet);
            }
        }

        TimesheetstatusEntity updatedTimesheetstatus = _timesheetstatusRepository.save(timesheetstatus);
        return mapper.timesheetstatusEntityToUpdateTimesheetstatusOutput(updatedTimesheetstatus);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(TimesheetstatusId timesheetstatusId) {
        TimesheetstatusEntity existing = _timesheetstatusRepository.findById(timesheetstatusId).orElse(null);
        _timesheetstatusRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindTimesheetstatusByIdOutput findById(TimesheetstatusId timesheetstatusId) {
        TimesheetstatusEntity foundTimesheetstatus = _timesheetstatusRepository
            .findById(timesheetstatusId)
            .orElse(null);
        if (foundTimesheetstatus == null) return null;

        return mapper.timesheetstatusEntityToFindTimesheetstatusByIdOutput(foundTimesheetstatus);
    }

    //Status
    // ReST API Call - GET /timesheetstatus/1/status
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetStatusOutput getStatus(TimesheetstatusId timesheetstatusId) {
        TimesheetstatusEntity foundTimesheetstatus = _timesheetstatusRepository
            .findById(timesheetstatusId)
            .orElse(null);
        if (foundTimesheetstatus == null) {
            logHelper.getLogger().error("There does not exist a timesheetstatus wth a id=%s", timesheetstatusId);
            return null;
        }
        StatusEntity re = foundTimesheetstatus.getStatus();
        return mapper.statusEntityToGetStatusOutput(re, foundTimesheetstatus);
    }

    //Timesheet
    // ReST API Call - GET /timesheetstatus/1/timesheet
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetTimesheetOutput getTimesheet(TimesheetstatusId timesheetstatusId) {
        TimesheetstatusEntity foundTimesheetstatus = _timesheetstatusRepository
            .findById(timesheetstatusId)
            .orElse(null);
        if (foundTimesheetstatus == null) {
            logHelper.getLogger().error("There does not exist a timesheetstatus wth a id=%s", timesheetstatusId);
            return null;
        }
        TimesheetEntity re = foundTimesheetstatus.getTimesheet();
        return mapper.timesheetEntityToGetTimesheetOutput(re, foundTimesheetstatus);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindTimesheetstatusByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<TimesheetstatusEntity> foundTimesheetstatus = _timesheetstatusRepository.findAll(search(search), pageable);
        List<TimesheetstatusEntity> timesheetstatusList = foundTimesheetstatus.getContent();
        Iterator<TimesheetstatusEntity> timesheetstatusIterator = timesheetstatusList.iterator();
        List<FindTimesheetstatusByIdOutput> output = new ArrayList<>();

        while (timesheetstatusIterator.hasNext()) {
            TimesheetstatusEntity timesheetstatus = timesheetstatusIterator.next();
            output.add(mapper.timesheetstatusEntityToFindTimesheetstatusByIdOutput(timesheetstatus));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QTimesheetstatusEntity timesheetstatus = QTimesheetstatusEntity.timesheetstatusEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(timesheetstatus, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    //       list.get(i).replace("%20","").trim().equals("id") ||
                    //       list.get(i).replace("%20","").trim().equals("id") ||
                    list.get(i).replace("%20", "").trim().equals("notes") ||
                    list.get(i).replace("%20", "").trim().equals("statuschangedate") ||
                    list.get(i).replace("%20", "").trim().equals("statusid") ||
                    list.get(i).replace("%20", "").trim().equals("timesheetid")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QTimesheetstatusEntity timesheetstatus,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("notes")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    timesheetstatus.notes.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    timesheetstatus.notes.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    timesheetstatus.notes.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("statuschangedate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    timesheetstatus.statuschangedate.eq(
                        SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())
                    )
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    timesheetstatus.statuschangedate.ne(
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
                        timesheetstatus.statuschangedate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(
                        timesheetstatus.statuschangedate.loe(endLocalDateTime)
                    ); else if (startLocalDateTime != null) builder.and(
                        timesheetstatus.statuschangedate.goe(startLocalDateTime)
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("statusid")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(timesheetstatus.statusid.eq(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(timesheetstatus.statusid.ne(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        timesheetstatus.statusid.between(
                            Long.valueOf(details.getValue().getStartingValue()),
                            Long.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        timesheetstatus.statusid.goe(Long.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        timesheetstatus.statusid.loe(Long.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("timesheetid")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(
                    timesheetstatus.timesheetid.eq(Long.valueOf(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(
                    timesheetstatus.timesheetid.ne(Long.valueOf(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        timesheetstatus.timesheetid.between(
                            Long.valueOf(details.getValue().getStartingValue()),
                            Long.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        timesheetstatus.timesheetid.goe(Long.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        timesheetstatus.timesheetid.loe(Long.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }

            if (details.getKey().replace("%20", "").trim().equals("status")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(
                    timesheetstatus.status.id.eq(Long.valueOf(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(
                    timesheetstatus.status.id.ne(Long.valueOf(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        timesheetstatus.status.id.between(
                            Long.valueOf(details.getValue().getStartingValue()),
                            Long.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        timesheetstatus.status.id.goe(Long.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        timesheetstatus.status.id.loe(Long.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("timesheet")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(
                    timesheetstatus.timesheet.id.eq(Long.valueOf(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(
                    timesheetstatus.timesheet.id.ne(Long.valueOf(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        timesheetstatus.timesheet.id.between(
                            Long.valueOf(details.getValue().getStartingValue()),
                            Long.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        timesheetstatus.timesheet.id.goe(Long.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        timesheetstatus.timesheet.id.loe(Long.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("statusid")) {
                builder.and(timesheetstatus.status.id.eq(Long.parseLong(joinCol.getValue())));
            }

            if (joinCol != null && joinCol.getKey().equals("status")) {
                builder.and(timesheetstatus.status.id.eq(Long.parseLong(joinCol.getValue())));
            }
        }
        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("timesheetid")) {
                builder.and(timesheetstatus.timesheet.id.eq(Long.parseLong(joinCol.getValue())));
            }

            if (joinCol != null && joinCol.getKey().equals("timesheet")) {
                builder.and(timesheetstatus.timesheet.id.eq(Long.parseLong(joinCol.getValue())));
            }
        }
        return builder;
    }

    public TimesheetstatusId parseTimesheetstatusKey(String keysString) {
        String[] keyEntries = keysString.split(",");
        TimesheetstatusId timesheetstatusId = new TimesheetstatusId();

        Map<String, String> keyMap = new HashMap<String, String>();
        if (keyEntries.length > 1) {
            for (String keyEntry : keyEntries) {
                String[] keyEntryArr = keyEntry.split("=");
                if (keyEntryArr.length > 1) {
                    keyMap.put(keyEntryArr[0], keyEntryArr[1]);
                } else {
                    return null;
                }
            }
        } else {
            String[] keyEntryArr = keysString.split("=");
            if (keyEntryArr.length > 1) {
                keyMap.put(keyEntryArr[0], keyEntryArr[1]);
            } else return null;
        }

        timesheetstatusId.setStatusid(Long.valueOf(keyMap.get("statusid")));
        timesheetstatusId.setTimesheetid(Long.valueOf(keyMap.get("timesheetid")));
        return timesheetstatusId;
    }
}
