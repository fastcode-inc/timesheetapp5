package com.fastcode.timesheetapp5.application.core.project;

import com.fastcode.timesheetapp5.application.core.project.dto.*;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.commons.search.*;
import com.fastcode.timesheetapp5.domain.core.customer.CustomerEntity;
import com.fastcode.timesheetapp5.domain.core.customer.ICustomerRepository;
import com.fastcode.timesheetapp5.domain.core.project.IProjectRepository;
import com.fastcode.timesheetapp5.domain.core.project.ProjectEntity;
import com.fastcode.timesheetapp5.domain.core.project.QProjectEntity;
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

@Service("projectAppService")
@RequiredArgsConstructor
public class ProjectAppService implements IProjectAppService {

    @Qualifier("projectRepository")
    @NonNull
    protected final IProjectRepository _projectRepository;

    @Qualifier("customerRepository")
    @NonNull
    protected final ICustomerRepository _customerRepository;

    @Qualifier("IProjectMapperImpl")
    @NonNull
    protected final IProjectMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateProjectOutput create(CreateProjectInput input) {
        ProjectEntity project = mapper.createProjectInputToProjectEntity(input);
        CustomerEntity foundCustomer = null;
        if (input.getCustomerid() != null) {
            foundCustomer = _customerRepository.findById(input.getCustomerid()).orElse(null);

            if (foundCustomer != null) {
                project.setCustomer(foundCustomer);
            }
        }

        ProjectEntity createdProject = _projectRepository.save(project);
        return mapper.projectEntityToCreateProjectOutput(createdProject);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateProjectOutput update(Long projectId, UpdateProjectInput input) {
        ProjectEntity project = mapper.updateProjectInputToProjectEntity(input);
        CustomerEntity foundCustomer = null;

        if (input.getCustomerid() != null) {
            foundCustomer = _customerRepository.findById(input.getCustomerid()).orElse(null);

            if (foundCustomer != null) {
                project.setCustomer(foundCustomer);
            }
        }

        ProjectEntity updatedProject = _projectRepository.save(project);
        return mapper.projectEntityToUpdateProjectOutput(updatedProject);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long projectId) {
        ProjectEntity existing = _projectRepository.findById(projectId).orElse(null);
        _projectRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindProjectByIdOutput findById(Long projectId) {
        ProjectEntity foundProject = _projectRepository.findById(projectId).orElse(null);
        if (foundProject == null) return null;

        return mapper.projectEntityToFindProjectByIdOutput(foundProject);
    }

    //Customer
    // ReST API Call - GET /project/1/customer
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetCustomerOutput getCustomer(Long projectId) {
        ProjectEntity foundProject = _projectRepository.findById(projectId).orElse(null);
        if (foundProject == null) {
            logHelper.getLogger().error("There does not exist a project wth a id=%s", projectId);
            return null;
        }
        CustomerEntity re = foundProject.getCustomer();
        return mapper.customerEntityToGetCustomerOutput(re, foundProject);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindProjectByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<ProjectEntity> foundProject = _projectRepository.findAll(search(search), pageable);
        List<ProjectEntity> projectList = foundProject.getContent();
        Iterator<ProjectEntity> projectIterator = projectList.iterator();
        List<FindProjectByIdOutput> output = new ArrayList<>();

        while (projectIterator.hasNext()) {
            ProjectEntity project = projectIterator.next();
            output.add(mapper.projectEntityToFindProjectByIdOutput(project));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QProjectEntity project = QProjectEntity.projectEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(project, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("customerid") ||
                    list.get(i).replace("%20", "").trim().equals("description") ||
                    list.get(i).replace("%20", "").trim().equals("enddate") ||
                    list.get(i).replace("%20", "").trim().equals("id") ||
                    list.get(i).replace("%20", "").trim().equals("name") ||
                    list.get(i).replace("%20", "").trim().equals("startdate")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QProjectEntity project,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("description")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    project.description.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    project.description.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    project.description.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("enddate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    project.enddate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    project.enddate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        project.enddate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(project.enddate.loe(endLocalDateTime)); else if (
                        startLocalDateTime != null
                    ) builder.and(project.enddate.goe(startLocalDateTime));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("id")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(project.id.eq(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(project.id.ne(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        project.id.between(
                            Long.valueOf(details.getValue().getStartingValue()),
                            Long.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        project.id.goe(Long.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        project.id.loe(Long.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("name")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    project.name.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    project.name.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    project.name.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("startdate")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    project.startdate.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) != null
                ) builder.and(
                    project.startdate.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    LocalDateTime startLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getStartingValue()
                    );
                    LocalDateTime endLocalDateTime = SearchUtils.stringToLocalDateTime(
                        details.getValue().getEndingValue()
                    );
                    if (startLocalDateTime != null && endLocalDateTime != null) builder.and(
                        project.startdate.between(startLocalDateTime, endLocalDateTime)
                    ); else if (endLocalDateTime != null) builder.and(
                        project.startdate.loe(endLocalDateTime)
                    ); else if (startLocalDateTime != null) builder.and(project.startdate.goe(startLocalDateTime));
                }
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("customerid")) {
                builder.and(project.customer.customerid.eq(Long.parseLong(joinCol.getValue())));
            }
        }
        return builder;
    }

    public Map<String, String> parseTasksJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("projectid", keysString);

        return joinColumnMap;
    }
}
