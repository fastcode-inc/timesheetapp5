package com.fastcode.timesheetapp5.application.core.status;

import com.fastcode.timesheetapp5.application.core.status.dto.*;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.commons.search.*;
import com.fastcode.timesheetapp5.domain.core.status.IStatusRepository;
import com.fastcode.timesheetapp5.domain.core.status.QStatusEntity;
import com.fastcode.timesheetapp5.domain.core.status.StatusEntity;
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

@Service("statusAppService")
@RequiredArgsConstructor
public class StatusAppService implements IStatusAppService {

    @Qualifier("statusRepository")
    @NonNull
    protected final IStatusRepository _statusRepository;

    @Qualifier("IStatusMapperImpl")
    @NonNull
    protected final IStatusMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateStatusOutput create(CreateStatusInput input) {
        StatusEntity status = mapper.createStatusInputToStatusEntity(input);

        StatusEntity createdStatus = _statusRepository.save(status);
        return mapper.statusEntityToCreateStatusOutput(createdStatus);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateStatusOutput update(Long statusId, UpdateStatusInput input) {
        StatusEntity status = mapper.updateStatusInputToStatusEntity(input);

        StatusEntity updatedStatus = _statusRepository.save(status);
        return mapper.statusEntityToUpdateStatusOutput(updatedStatus);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long statusId) {
        StatusEntity existing = _statusRepository.findById(statusId).orElse(null);
        _statusRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindStatusByIdOutput findById(Long statusId) {
        StatusEntity foundStatus = _statusRepository.findById(statusId).orElse(null);
        if (foundStatus == null) return null;

        return mapper.statusEntityToFindStatusByIdOutput(foundStatus);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindStatusByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<StatusEntity> foundStatus = _statusRepository.findAll(search(search), pageable);
        List<StatusEntity> statusList = foundStatus.getContent();
        Iterator<StatusEntity> statusIterator = statusList.iterator();
        List<FindStatusByIdOutput> output = new ArrayList<>();

        while (statusIterator.hasNext()) {
            StatusEntity status = statusIterator.next();
            output.add(mapper.statusEntityToFindStatusByIdOutput(status));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QStatusEntity status = QStatusEntity.statusEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(status, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("description") ||
                    list.get(i).replace("%20", "").trim().equals("id")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QStatusEntity status,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("description")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    status.description.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    status.description.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    status.description.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("id")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(status.id.eq(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(status.id.ne(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        status.id.between(
                            Long.valueOf(details.getValue().getStartingValue()),
                            Long.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        status.id.goe(Long.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        status.id.loe(Long.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
        }

        return builder;
    }

    public Map<String, String> parseTimesheetstatusJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("statusid", keysString);

        return joinColumnMap;
    }
}
