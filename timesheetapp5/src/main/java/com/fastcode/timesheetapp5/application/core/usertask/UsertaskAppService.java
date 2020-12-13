package com.fastcode.timesheetapp5.application.core.usertask;

import com.fastcode.timesheetapp5.application.core.usertask.dto.*;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.commons.search.*;
import com.fastcode.timesheetapp5.domain.core.authorization.users.IUsersRepository;
import com.fastcode.timesheetapp5.domain.core.authorization.users.UsersEntity;
import com.fastcode.timesheetapp5.domain.core.task.ITaskRepository;
import com.fastcode.timesheetapp5.domain.core.task.TaskEntity;
import com.fastcode.timesheetapp5.domain.core.usertask.IUsertaskRepository;
import com.fastcode.timesheetapp5.domain.core.usertask.QUsertaskEntity;
import com.fastcode.timesheetapp5.domain.core.usertask.UsertaskEntity;
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

@Service("usertaskAppService")
@RequiredArgsConstructor
public class UsertaskAppService implements IUsertaskAppService {

    @Qualifier("usertaskRepository")
    @NonNull
    protected final IUsertaskRepository _usertaskRepository;

    @Qualifier("taskRepository")
    @NonNull
    protected final ITaskRepository _taskRepository;

    @Qualifier("usersRepository")
    @NonNull
    protected final IUsersRepository _usersRepository;

    @Qualifier("IUsertaskMapperImpl")
    @NonNull
    protected final IUsertaskMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateUsertaskOutput create(CreateUsertaskInput input) {
        UsertaskEntity usertask = mapper.createUsertaskInputToUsertaskEntity(input);
        TaskEntity foundTask = null;
        UsersEntity foundUsers = null;
        if (input.getTaskid() != null) {
            foundTask = _taskRepository.findById(input.getTaskid()).orElse(null);

            if (foundTask != null) {
                usertask.setTask(foundTask);
            }
        }
        if (input.getUserid() != null) {
            foundUsers = _usersRepository.findById(input.getUserid()).orElse(null);

            if (foundUsers != null) {
                usertask.setUsers(foundUsers);
            }
        }

        UsertaskEntity createdUsertask = _usertaskRepository.save(usertask);
        return mapper.usertaskEntityToCreateUsertaskOutput(createdUsertask);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateUsertaskOutput update(Long usertaskId, UpdateUsertaskInput input) {
        UsertaskEntity usertask = mapper.updateUsertaskInputToUsertaskEntity(input);
        TaskEntity foundTask = null;
        UsersEntity foundUsers = null;

        if (input.getTaskid() != null) {
            foundTask = _taskRepository.findById(input.getTaskid()).orElse(null);

            if (foundTask != null) {
                usertask.setTask(foundTask);
            }
        }

        if (input.getUserid() != null) {
            foundUsers = _usersRepository.findById(input.getUserid()).orElse(null);

            if (foundUsers != null) {
                usertask.setUsers(foundUsers);
            }
        }

        UsertaskEntity updatedUsertask = _usertaskRepository.save(usertask);
        return mapper.usertaskEntityToUpdateUsertaskOutput(updatedUsertask);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long usertaskId) {
        UsertaskEntity existing = _usertaskRepository.findById(usertaskId).orElse(null);
        _usertaskRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindUsertaskByIdOutput findById(Long usertaskId) {
        UsertaskEntity foundUsertask = _usertaskRepository.findById(usertaskId).orElse(null);
        if (foundUsertask == null) return null;

        return mapper.usertaskEntityToFindUsertaskByIdOutput(foundUsertask);
    }

    //Task
    // ReST API Call - GET /usertask/1/task
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetTaskOutput getTask(Long usertaskId) {
        UsertaskEntity foundUsertask = _usertaskRepository.findById(usertaskId).orElse(null);
        if (foundUsertask == null) {
            logHelper.getLogger().error("There does not exist a usertask wth a id=%s", usertaskId);
            return null;
        }
        TaskEntity re = foundUsertask.getTask();
        return mapper.taskEntityToGetTaskOutput(re, foundUsertask);
    }

    //Users
    // ReST API Call - GET /usertask/1/users
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetUsersOutput getUsers(Long usertaskId) {
        UsertaskEntity foundUsertask = _usertaskRepository.findById(usertaskId).orElse(null);
        if (foundUsertask == null) {
            logHelper.getLogger().error("There does not exist a usertask wth a id=%s", usertaskId);
            return null;
        }
        UsersEntity re = foundUsertask.getUsers();
        return mapper.usersEntityToGetUsersOutput(re, foundUsertask);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindUsertaskByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<UsertaskEntity> foundUsertask = _usertaskRepository.findAll(search(search), pageable);
        List<UsertaskEntity> usertaskList = foundUsertask.getContent();
        Iterator<UsertaskEntity> usertaskIterator = usertaskList.iterator();
        List<FindUsertaskByIdOutput> output = new ArrayList<>();

        while (usertaskIterator.hasNext()) {
            UsertaskEntity usertask = usertaskIterator.next();
            output.add(mapper.usertaskEntityToFindUsertaskByIdOutput(usertask));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QUsertaskEntity usertask = QUsertaskEntity.usertaskEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(usertask, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("taskid") ||
                    list.get(i).replace("%20", "").trim().equals("userid") ||
                    list.get(i).replace("%20", "").trim().equals("id")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QUsertaskEntity usertask,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("id")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(usertask.id.eq(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(usertask.id.ne(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        usertask.id.between(
                            Long.valueOf(details.getValue().getStartingValue()),
                            Long.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        usertask.id.goe(Long.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        usertask.id.loe(Long.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }

            if (details.getKey().replace("%20", "").trim().equals("task")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(usertask.task.id.eq(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(usertask.task.id.ne(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        usertask.task.id.between(
                            Long.valueOf(details.getValue().getStartingValue()),
                            Long.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        usertask.task.id.goe(Long.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        usertask.task.id.loe(Long.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("users")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(usertask.users.id.eq(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(usertask.users.id.ne(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        usertask.users.id.between(
                            Long.valueOf(details.getValue().getStartingValue()),
                            Long.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        usertask.users.id.goe(Long.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        usertask.users.id.loe(Long.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("taskid")) {
                builder.and(usertask.task.id.eq(Long.parseLong(joinCol.getValue())));
            }

            if (joinCol != null && joinCol.getKey().equals("task")) {
                builder.and(usertask.task.id.eq(Long.parseLong(joinCol.getValue())));
            }
        }
        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("userid")) {
                builder.and(usertask.users.id.eq(Long.parseLong(joinCol.getValue())));
            }

            if (joinCol != null && joinCol.getKey().equals("users")) {
                builder.and(usertask.users.id.eq(Long.parseLong(joinCol.getValue())));
            }
        }
        return builder;
    }
}
