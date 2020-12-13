package com.fastcode.timesheetapp5.addons.reporting.application.reportversion;

import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.CreateReportversionInput;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.CreateReportversionOutput;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.FindReportversionByIdOutput;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.GetUsersOutput;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.UpdateReportversionInput;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.UpdateReportversionOutput;
import com.fastcode.timesheetapp5.addons.reporting.domain.report.IReportRepository;
import com.fastcode.timesheetapp5.addons.reporting.domain.report.ReportEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.reportversion.IReportversionRepository;
import com.fastcode.timesheetapp5.addons.reporting.domain.reportversion.QReportversionEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.reportversion.ReportversionEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.reportversion.ReportversionId;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.commons.search.SearchCriteria;
import com.fastcode.timesheetapp5.commons.search.SearchFields;
import com.fastcode.timesheetapp5.domain.core.authorization.users.UsersEntity;
import com.fastcode.timesheetapp5.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.querydsl.core.BooleanBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("reportversionAppService")
public class ReportversionAppService implements IReportversionAppService {

    static final int case1 = 1;
    static final int case2 = 2;
    static final int case3 = 3;

    @Autowired
    @Qualifier("reportversionRepository")
    protected IReportversionRepository _reportversionRepository;

    @Autowired
    @Qualifier("usersRepositoryExtended")
    protected IUsersRepositoryExtended _usersRepository;

    @Autowired
    @Qualifier("reportRepository")
    protected IReportRepository _reportRepository;

    @Autowired
    @Qualifier("IReportversionMapperImpl")
    protected IReportversionMapper mapper;

    @Autowired
    protected LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateReportversionOutput create(CreateReportversionInput input) {
        ReportversionEntity reportversion = mapper.createReportversionInputToReportversionEntity(input);
        if (input.getUserId() != null) {
            UsersEntity foundUsers = _usersRepository.findById(input.getUserId()).orElse(null);
            reportversion.setUsers(foundUsers);
        }

        if (input.getReportId() != null) {
            ReportEntity foundReport = _reportRepository.findById(input.getReportId()).orElse(null);
            reportversion.setReport(foundReport);
        }

        reportversion.setReportVersion("running");
        reportversion.setIsRefreshed(true);
        ReportversionEntity createdRunningReportversion = _reportversionRepository.save(reportversion);

        reportversion = mapper.createReportversionInputToReportversionEntity(input);
        reportversion.setReportVersion("published");

        ReportversionEntity createdPublishedReportversion = _reportversionRepository.save(reportversion);

        return mapper.reportversionEntityToCreateReportversionOutput(createdRunningReportversion);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateReportversionOutput update(ReportversionId reportversionId, UpdateReportversionInput input) {
        ReportversionEntity reportversion = mapper.updateReportversionInputToReportversionEntity(input);

        if (input.getUserId() != null) {
            UsersEntity foundUsers = _usersRepository.findById(input.getUserId()).orElse(null);
            reportversion.setUsers(foundUsers);
        }

        if (input.getReportId() != null) {
            ReportEntity foundReport = _reportRepository.findById(input.getReportId()).orElse(null);
            reportversion.setReport(foundReport);
        }

        reportversion.setReportVersion(reportversionId.getReportVersion());
        ReportversionEntity updatedReportversion = _reportversionRepository.save(reportversion);

        return mapper.reportversionEntityToUpdateReportversionOutput(updatedReportversion);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(ReportversionId reportversionId) {
        ReportversionEntity existing = _reportversionRepository.findById(reportversionId).orElse(null);
        _reportversionRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindReportversionByIdOutput findById(ReportversionId reportversionId) {
        ReportversionEntity foundReportversion = _reportversionRepository.findById(reportversionId).orElse(null);
        if (foundReportversion == null) return null;

        FindReportversionByIdOutput output = mapper.reportversionEntityToFindReportversionByIdOutput(
            foundReportversion
        );
        return output;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetUsersOutput getUsers(ReportversionId reportversionId) {
        ReportversionEntity foundReportversion = _reportversionRepository.findById(reportversionId).orElse(null);
        if (foundReportversion == null) {
            logHelper.getLogger().error("There does not exist a reportversion wth a id=%s", reportversionId);
            return null;
        }
        UsersEntity re = foundReportversion.getUsers();
        return mapper.usersEntityToGetUsersOutput(re, foundReportversion);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindReportversionByIdOutput> findByUsersId(Long userId) {
        List<ReportversionEntity> reportList = _reportversionRepository.findByUsersId(userId);
        if (reportList == null) return null;

        Iterator<ReportversionEntity> reportIterator = reportList.iterator();
        List<FindReportversionByIdOutput> output = new ArrayList<>();

        while (reportIterator.hasNext()) {
            output.add(mapper.reportversionEntityToFindReportversionByIdOutput(reportIterator.next()));
        }

        return output;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindReportversionByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<ReportversionEntity> foundReportversion = _reportversionRepository.findAll(search(search), pageable);
        List<ReportversionEntity> reportList = foundReportversion.getContent();
        Iterator<ReportversionEntity> reportIterator = reportList.iterator();
        List<FindReportversionByIdOutput> output = new ArrayList<>();

        while (reportIterator.hasNext()) {
            output.add(mapper.reportversionEntityToFindReportversionByIdOutput(reportIterator.next()));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QReportversionEntity reportversion = QReportversionEntity.reportversionEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(reportversion, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("users") ||
                    list.get(i).replace("%20", "").trim().equals("userId") ||
                    list.get(i).replace("%20", "").trim().equals("ctype") ||
                    list.get(i).replace("%20", "").trim().equals("description") ||
                    list.get(i).replace("%20", "").trim().equals("id") ||
                    list.get(i).replace("%20", "").trim().equals("query") ||
                    list.get(i).replace("%20", "").trim().equals("reportType") ||
                    list.get(i).replace("%20", "").trim().equals("reportdashboard") ||
                    list.get(i).replace("%20", "").trim().equals("title")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QReportversionEntity reportversion,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("ctype")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    reportversion.ctype.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    reportversion.ctype.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    reportversion.ctype.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("description")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    reportversion.description.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    reportversion.description.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    reportversion.description.ne(details.getValue().getSearchValue())
                );
            }

            if (details.getKey().replace("%20", "").trim().equals("reportType")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    reportversion.reportType.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    reportversion.reportType.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    reportversion.reportType.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("title")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    reportversion.title.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    reportversion.title.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    reportversion.title.ne(details.getValue().getSearchValue())
                );
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("userId")) {
                builder.and(reportversion.users.id.eq(Long.parseLong(joinCol.getValue())));
            }
        }
        return builder;
    }

    public Map<String, String> parsedashboardversionreportJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("reportId", keysString);
        return joinColumnMap;
    }
}
