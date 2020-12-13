package com.fastcode.timesheetapp5.addons.reporting.application.report;

import com.fastcode.timesheetapp5.addons.reporting.application.report.dto.*;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.IReportversionAppService;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.IReportversionMapper;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.CreateReportversionInput;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.CreateReportversionOutput;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.UpdateReportversionInput;
import com.fastcode.timesheetapp5.addons.reporting.application.reportversion.dto.UpdateReportversionOutput;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversionreport.DashboardversionreportEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversionreport.IDashboardversionreportRepository;
import com.fastcode.timesheetapp5.addons.reporting.domain.report.IReportRepository;
import com.fastcode.timesheetapp5.addons.reporting.domain.report.QReportEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.report.ReportEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.reportversion.IReportversionRepository;
import com.fastcode.timesheetapp5.addons.reporting.domain.reportversion.ReportversionEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.reportversion.ReportversionId;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp5.commons.search.*;
import com.fastcode.timesheetapp5.domain.core.authorization.users.UsersEntity;
import com.fastcode.timesheetapp5.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.querydsl.core.BooleanBuilder;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("reportAppService")
public class ReportAppService implements IReportAppService {

    static final int case1 = 1;
    static final int case2 = 2;
    static final int case3 = 3;

    @Autowired
    @Qualifier("reportRepository")
    protected IReportRepository _reportRepository;

    @Autowired
    @Qualifier("reportversionRepository")
    protected IReportversionRepository _reportversionRepository;

    @Autowired
    @Qualifier("usersRepositoryExtended")
    protected IUsersRepositoryExtended _usersRepository;

    @Autowired
    @Qualifier("dashboardversionreportRepository")
    protected IDashboardversionreportRepository _reportDashboardRepository;

    @Autowired
    @Qualifier("reportversionAppService")
    protected IReportversionAppService _reportversionAppservice;

    @Autowired
    @Qualifier("IReportMapperImpl")
    protected IReportMapper mapper;

    @Autowired
    @Qualifier("IReportversionMapperImpl")
    protected IReportversionMapper reportversionMapper;

    @Autowired
    protected LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateReportOutput create(CreateReportInput input) {
        ReportEntity report = mapper.createReportInputToReportEntity(input);
        if (input.getOwnerId() != null) {
            UsersEntity foundUsers = _usersRepository.findById(input.getOwnerId()).orElse(null);

            if (foundUsers != null) {
                report.setUsers(foundUsers);
            }
        }

        ReportEntity createdReport = _reportRepository.save(report);
        CreateReportversionInput reportversion = mapper.createReportInputToCreateReportversionInput(input);
        reportversion.setReportId(createdReport.getId());

        CreateReportversionOutput reportversionOutput = _reportversionAppservice.create(reportversion);
        return mapper.reportEntityAndCreateReportversionOutputToCreateReportOutput(createdReport, reportversionOutput);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateReportOutput update(Long reportId, UpdateReportInput input) {
        ReportversionId reportversionId = new ReportversionId(input.getUserId(), reportId, "running");

        ReportversionEntity rv = _reportversionRepository.findById(reportversionId).orElse(null);
        UpdateReportversionInput reportversion = mapper.updateReportInputToUpdateReportversionInput(input);
        reportversion.setReportId(rv.getReport().getId());
        reportversion.setReportVersion(rv.getReportVersion());
        reportversion.setVersiono(rv.getVersiono());
        reportversion.setIsRefreshed(false);

        UpdateReportversionOutput reportversionOutput = _reportversionAppservice.update(reportversionId, reportversion);

        ReportEntity foundReport = _reportRepository.findById(reportId).orElse(null);
        if (foundReport.getUsers() != null && foundReport.getUsers().getId() == input.getUserId()) {
            foundReport.setIsPublished(false);
            foundReport = _reportRepository.save(foundReport);
        }

        return mapper.reportEntityAndUpdateReportversionOutputToUpdateReportOutput(foundReport, reportversionOutput);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long reportId, Long userId) {
        ReportEntity existing = _reportRepository.findById(reportId).orElse(null);

        List<DashboardversionreportEntity> dvrList = _reportDashboardRepository.findByReportId(reportId);

        for (DashboardversionreportEntity dvr : dvrList) {
            _reportDashboardRepository.delete(dvr);
        }

        _reportversionAppservice.delete(new ReportversionId(userId, reportId, "running"));
        _reportversionAppservice.delete(new ReportversionId(userId, reportId, "published"));

        _reportRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindReportByIdOutput findById(Long reportId) {
        ReportEntity foundReport = _reportRepository.findById(reportId).orElse(null);
        if (foundReport == null) return null;

        ReportversionEntity reportversion = _reportversionRepository
            .findById(new ReportversionId(foundReport.getUsers().getId(), foundReport.getId(), "running"))
            .orElse(null);

        FindReportByIdOutput output = mapper.reportEntityToFindReportByIdOutput(foundReport, reportversion);
        return output;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindReportByIdOutput findByReportIdAndUsersId(Long reportId, Long userId, String version) {
        ReportEntity foundReport = _reportRepository.findByReportIdAndUsersId(reportId, userId);
        if (foundReport == null) {
            return null;
        }

        ReportversionEntity reportVersion, publishedversion;
        publishedversion =
            _reportversionRepository.findById(new ReportversionId(userId, reportId, "published")).orElse(null);

        if (StringUtils.isNotBlank(version) && version.equalsIgnoreCase("published")) {
            reportVersion = publishedversion;
        } else {
            reportVersion =
                _reportversionRepository.findById(new ReportversionId(userId, reportId, "running")).orElse(null);
        }
        FindReportByIdOutput output = mapper.reportEntitiesToFindReportByIdOutput(foundReport, reportVersion);

        return output;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ReportDetailsOutput publishReport(Long userId, Long reportId) {
        ReportEntity foundReport = _reportRepository.findById(reportId).orElse(null);

        foundReport.setIsPublished(true);
        foundReport = _reportRepository.save(foundReport);
        ReportversionEntity foundReportversion = _reportversionRepository
            .findById(new ReportversionId(userId, reportId, "running"))
            .orElse(null);
        ReportversionEntity foundPublishedversion = _reportversionRepository
            .findById(new ReportversionId(userId, reportId, "published"))
            .orElse(null);
        ReportversionEntity publishedVersion = reportversionMapper.reportversionEntityToReportversionEntity(
            foundReportversion,
            userId,
            "published"
        );

        if (foundPublishedversion != null) {
            publishedVersion.setVersiono(foundPublishedversion.getVersiono());
        } else publishedVersion.setVersiono(0L);

        _reportversionRepository.save(publishedVersion);
        foundReportversion.setIsRefreshed(true);
        _reportversionRepository.save(foundReportversion);

        return mapper.reportEntitiesToReportDetailsOutput(foundReport, foundReportversion);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ReportDetailsOutput refreshReport(Long userId, Long reportId) {
        ReportEntity foundReport = _reportRepository.findById(reportId).orElse(null);

        if (foundReport != null && foundReport.getUsers() != null && foundReport.getUsers().getId() == userId) {
            ReportversionEntity ownerPublishedversion = _reportversionRepository
                .findById(new ReportversionId(userId, reportId, "published"))
                .orElse(null);
            ReportversionEntity ownerRunningversion = _reportversionRepository
                .findById(new ReportversionId(userId, reportId, "running"))
                .orElse(null);

            UsersEntity foundUsers = _usersRepository.findById(userId).orElse(null);
            ReportversionEntity updatedVersion = reportversionMapper.reportversionEntityToReportversionEntity(
                ownerPublishedversion,
                userId,
                "running"
            );
            updatedVersion.setUsers(foundUsers);
            updatedVersion.setVersiono(ownerRunningversion.getVersiono());
            updatedVersion.setIsRefreshed(true);
            _reportversionRepository.save(updatedVersion);

            ReportversionEntity runningversion = _reportversionRepository
                .findById(new ReportversionId(userId, reportId, "running"))
                .orElse(null);
            ReportDetailsOutput output = mapper.reportEntitiesToReportDetailsOutput(foundReport, runningversion);
            return output;
        }

        return null;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ReportDetailsOutput> getReports(Long userId, String search, Pageable pageable) throws Exception {
        Page<ReportDetailsOutput> foundReport = _reportRepository.getAllReportsByUsersId(userId, search, pageable);
        List<ReportDetailsOutput> reportList = foundReport.getContent();

        return reportList;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindReportByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<ReportEntity> foundReport = _reportRepository.findAll(search(search), pageable);
        List<ReportEntity> reportList = foundReport.getContent();
        Iterator<ReportEntity> reportIterator = reportList.iterator();
        List<FindReportByIdOutput> output = new ArrayList<>();

        while (reportIterator.hasNext()) {
            ReportEntity report = reportIterator.next();
            ReportversionEntity reportVersion = _reportversionRepository
                .findById(new ReportversionId(report.getUsers().getId(), report.getId(), "running"))
                .orElse(null);
            FindReportByIdOutput reportOutput = mapper.reportEntitiesToFindReportByIdOutput(report, reportVersion);
            output.add(reportOutput);
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QReportEntity report = QReportEntity.reportEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(report, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("userId") ||
                    list.get(i).replace("%20", "").trim().equals("ctype") ||
                    list.get(i).replace("%20", "").trim().equals("description") ||
                    list.get(i).replace("%20", "").trim().equals("id") ||
                    list.get(i).replace("%20", "").trim().equals("query") ||
                    list.get(i).replace("%20", "").trim().equals("reportType") ||
                    list.get(i).replace("%20", "").trim().equals("reportdashboard") ||
                    list.get(i).replace("%20", "").trim().equals("title") ||
                    list.get(i).replace("%20", "").trim().equals("user")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QReportEntity report,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("isPublished")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(
                    report.isPublished.eq(Boolean.parseBoolean(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(report.isPublished.ne(Boolean.parseBoolean(details.getValue().getSearchValue())));
            }
        }
        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("ownerId")) {
                builder.and(report.users.id.eq(Long.parseLong(joinCol.getValue())));
            }
        }
        return builder;
    }

    public Map<String, String> parseReportdashboardJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("reportId", keysString);
        return joinColumnMap;
    }
}
