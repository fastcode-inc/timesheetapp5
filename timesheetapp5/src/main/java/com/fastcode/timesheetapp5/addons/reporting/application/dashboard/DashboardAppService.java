package com.fastcode.timesheetapp5.addons.reporting.application.dashboard;

import com.fastcode.timesheetapp5.addons.reporting.application.dashboard.dto.*;
import com.fastcode.timesheetapp5.addons.reporting.application.dashboardversion.IDashboardversionAppService;
import com.fastcode.timesheetapp5.addons.reporting.application.dashboardversion.IDashboardversionMapper;
import com.fastcode.timesheetapp5.addons.reporting.application.dashboardversion.dto.CreateDashboardversionInput;
import com.fastcode.timesheetapp5.addons.reporting.application.dashboardversion.dto.CreateDashboardversionOutput;
import com.fastcode.timesheetapp5.addons.reporting.application.dashboardversion.dto.UpdateDashboardversionInput;
import com.fastcode.timesheetapp5.addons.reporting.application.dashboardversion.dto.UpdateDashboardversionOutput;
import com.fastcode.timesheetapp5.addons.reporting.application.dashboardversionreport.IDashboardversionreportAppService;
import com.fastcode.timesheetapp5.addons.reporting.application.report.IReportAppService;
import com.fastcode.timesheetapp5.addons.reporting.application.report.IReportMapper;
import com.fastcode.timesheetapp5.addons.reporting.application.report.dto.CreateReportInput;
import com.fastcode.timesheetapp5.addons.reporting.application.report.dto.CreateReportOutput;
import com.fastcode.timesheetapp5.addons.reporting.application.report.dto.FindReportByIdOutput;
import com.fastcode.timesheetapp5.addons.reporting.application.report.dto.UpdateReportInput;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboard.DashboardEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboard.IDashboardRepository;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboard.QDashboardEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversion.DashboardversionEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversion.DashboardversionId;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversion.IDashboardversionRepository;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversionreport.DashboardversionreportEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversionreport.DashboardversionreportId;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversionreport.IDashboardversionreportRepository;
import com.fastcode.timesheetapp5.addons.reporting.domain.report.IReportRepository;
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
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("dashboardAppService")
public class DashboardAppService implements IDashboardAppService {

    static final int case1 = 1;
    static final int case2 = 2;
    static final int case3 = 3;

    @Autowired
    @Qualifier("dashboardRepository")
    protected IDashboardRepository _dashboardRepository;

    @Autowired
    @Qualifier("dashboardversionreportAppService")
    protected IDashboardversionreportAppService _reportDashboardAppService;

    @Autowired
    @Qualifier("reportAppService")
    protected IReportAppService _reportAppService;

    @Autowired
    @Qualifier("dashboardversionAppService")
    protected IDashboardversionAppService _dashboardversionAppservice;

    @Autowired
    @Qualifier("IDashboardversionMapperImpl")
    protected IDashboardversionMapper dashboardversionMapper;

    @Autowired
    @Qualifier("dashboardversionRepository")
    protected IDashboardversionRepository _dashboardversionRepository;

    @Autowired
    @Qualifier("reportversionRepository")
    protected IReportversionRepository _reportversionRepository;

    @Autowired
    @Qualifier("dashboardversionreportRepository")
    protected IDashboardversionreportRepository _reportDashboardRepository;

    @Autowired
    @Qualifier("usersRepositoryExtended")
    protected IUsersRepositoryExtended _usersRepository;

    @Autowired
    @Qualifier("IDashboardMapperImpl")
    protected IDashboardMapper mapper;

    @Autowired
    @Qualifier("IReportMapperImpl")
    protected IReportMapper reportMapper;

    @Autowired
    @Qualifier("reportRepository")
    protected IReportRepository _reportRepository;

    @Autowired
    protected LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateDashboardOutput create(CreateDashboardInput input) {
        DashboardEntity dashboard = mapper.createDashboardInputToDashboardEntity(input);
        if (input.getOwnerId() != null) {
            UsersEntity foundUsers = _usersRepository.findById(input.getOwnerId()).orElse(null);

            if (foundUsers != null) {
                dashboard.setUsers(foundUsers);
            }
        }

        DashboardEntity createdDashboard = _dashboardRepository.save(dashboard);
        CreateDashboardversionInput dashboardversion = mapper.creatDashboardInputToCreateDashboardversionInput(input);
        dashboardversion.setDashboardId(createdDashboard.getId());

        CreateDashboardversionOutput dashboardversionOutput = _dashboardversionAppservice.create(dashboardversion);

        return mapper.dashboardEntityAndCreateDashboardversionOutputToCreateDashboardOutput(
            createdDashboard,
            dashboardversionOutput
        );
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateDashboardOutput update(Long dashboardId, UpdateDashboardInput input) {
        DashboardversionId dashboardversionId = new DashboardversionId(input.getUserId(), dashboardId, "running");

        DashboardversionEntity rv = _dashboardversionRepository.findById(dashboardversionId).orElse(null);
        UpdateDashboardversionInput dashboardversion = mapper.updateDashboardInputToUpdateDashboardversionInput(input);
        dashboardversion.setDashboardId(rv.getDashboard().getId());
        dashboardversion.setDashboardVersion(rv.getDashboardVersion());
        dashboardversion.setVersiono(rv.getVersiono());
        dashboardversion.setIsRefreshed(false);

        UpdateDashboardversionOutput dashboardversionOutput = _dashboardversionAppservice.update(
            dashboardversionId,
            dashboardversion
        );
        DashboardEntity foundDashboard = _dashboardRepository.findById(dashboardId).orElse(null);
        if (foundDashboard.getUsers() != null && foundDashboard.getUsers().getId() == input.getUserId()) {
            foundDashboard.setIsPublished(false);
            foundDashboard = _dashboardRepository.save(foundDashboard);
        }

        Long count = 0L;
        for (UpdateReportInput reportInput : input.getReportDetails()) {
            DashboardversionreportEntity dashboardreport = _reportDashboardRepository
                .findById(new DashboardversionreportId(dashboardId, input.getUserId(), "running", reportInput.getId()))
                .orElse(null);
            if (reportInput.getReportWidth() != null) {
                dashboardreport.setReportWidth(reportInput.getReportWidth());
            } else {
                dashboardreport.setReportWidth("mediumchart");
            }
            dashboardreport.setOrderId(count);
            count++;

            DashboardversionreportEntity dashboardVersionReport = _reportDashboardRepository.save(dashboardreport);
        }

        return mapper.dashboardEntityAndUpdateDashboardversionOutputToUpdateDashboardOutput(
            foundDashboard,
            dashboardversionOutput
        );
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long dashboardId, Long userId) {
        DashboardEntity existing = _dashboardRepository.findById(dashboardId).orElse(null);
        List<DashboardversionreportEntity> dvrList = _reportDashboardRepository.findByDashboardId(dashboardId);

        for (DashboardversionreportEntity dvr : dvrList) {
            _reportDashboardRepository.delete(dvr);
        }

        _dashboardversionAppservice.delete(new DashboardversionId(userId, dashboardId, "running"));
        _dashboardversionAppservice.delete(new DashboardversionId(userId, dashboardId, "published"));

        _dashboardRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteReportFromDashboard(Long dashboardId, Long reportId, Long userId) {
        _reportDashboardAppService.delete(new DashboardversionreportId(dashboardId, userId, "running", reportId));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindDashboardByIdOutput findById(Long dashboardId) {
        DashboardEntity foundDashboard = _dashboardRepository.findById(dashboardId).orElse(null);
        if (foundDashboard == null) return null;

        FindDashboardByIdOutput output = mapper.dashboardEntityToFindDashboardByIdOutput(foundDashboard);
        return output;
    }

    public List<FindReportByIdOutput> setReportsList(Long dashboardId, Long userId, String version) {
        List<DashboardversionreportEntity> reportDashboardList = _reportDashboardRepository.findByDashboardIdAndVersionAndUsersId(
            dashboardId,
            version,
            userId
        );

        List<FindReportByIdOutput> reportDetails = new ArrayList<>();
        for (DashboardversionreportEntity rd : reportDashboardList) {
            ReportversionEntity reportversion = _reportversionRepository
                .findById(new ReportversionId(rd.getUserId(), rd.getReportId(), version))
                .orElse(null);
            FindReportByIdOutput output = reportMapper.reportEntitiesToFindReportByIdOutput(
                rd.getReport(),
                reportversion
            );
            output.setOrderId(rd.getOrderId());
            output.setReportWidth(rd.getReportWidth());
            reportDetails.add(output);
        }

        List<FindReportByIdOutput> sortedReports = reportDetails
            .stream()
            .sorted(Comparator.comparing(FindReportByIdOutput::getOrderId))
            .collect(Collectors.toList());

        return sortedReports;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindDashboardByIdOutput findByDashboardIdAndUsersId(Long dashboardId, Long userId, String version) {
        DashboardEntity foundDashboard = _dashboardRepository.findByDashboardIdAndUsersId(dashboardId, userId);
        if (foundDashboard == null) return null;

        DashboardversionEntity dashboardVersion, publishedversion;
        publishedversion =
            _dashboardversionRepository.findById(new DashboardversionId(userId, dashboardId, "published")).orElse(null);
        if (StringUtils.isNotBlank(version) && version.equalsIgnoreCase("published")) {
            dashboardVersion = publishedversion;
        } else {
            dashboardVersion =
                _dashboardversionRepository
                    .findById(new DashboardversionId(userId, dashboardId, "running"))
                    .orElse(null);
        }
        FindDashboardByIdOutput output = mapper.dashboardEntitiesToFindDashboardByIdOutput(
            foundDashboard,
            dashboardVersion
        );

        return output;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<DashboardDetailsOutput> getDashboards(Long userId, String search, Pageable pageable) throws Exception {
        Page<DashboardDetailsOutput> foundDashboard = _dashboardRepository.getAllDashboardsByUsersId(
            userId,
            search,
            pageable
        );
        List<DashboardDetailsOutput> dashboardList = foundDashboard.getContent();

        return dashboardList;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<DashboardDetailsOutput> getAvailableDashboards(
        Long userId,
        Long reportId,
        String search,
        Pageable pageable
    ) throws Exception {
        Page<DashboardDetailsOutput> foundDashboard = _dashboardRepository.getAvailableDashboardsByUsersId(
            userId,
            reportId,
            search,
            pageable
        );
        List<DashboardDetailsOutput> dashboardList = foundDashboard.getContent();

        return dashboardList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public FindDashboardByIdOutput publishDashboard(Long userId, Long dashboardId) {
        DashboardEntity foundDashboard = _dashboardRepository.findById(dashboardId).orElse(null);
        foundDashboard.setIsPublished(true);
        foundDashboard = _dashboardRepository.save(foundDashboard);

        DashboardversionEntity foundDashboardversion = _dashboardversionRepository
            .findById(new DashboardversionId(userId, dashboardId, "running"))
            .orElse(null);
        DashboardversionEntity foundPublishedversion = _dashboardversionRepository
            .findById(new DashboardversionId(userId, dashboardId, "published"))
            .orElse(null);
        DashboardversionEntity publishedVersion = dashboardversionMapper.dashboardversionEntityToDashboardversionEntity(
            foundDashboardversion,
            userId,
            "published"
        );

        if (foundPublishedversion != null) {
            publishedVersion.setVersiono(foundPublishedversion.getVersiono());
        } else publishedVersion.setVersiono(0L);

        _dashboardversionRepository.save(publishedVersion);
        foundDashboardversion.setIsRefreshed(true);
        _dashboardversionRepository.save(foundDashboardversion);

        //check if report is added in running version
        List<DashboardversionreportEntity> dashboardreportList = _reportDashboardRepository.findByDashboardIdAndVersionAndUsersId(
            dashboardId,
            "running",
            userId
        );

        for (DashboardversionreportEntity dashboardreport : dashboardreportList) {
            DashboardversionreportEntity publishedDashboardreport = _reportDashboardRepository
                .findById(new DashboardversionreportId(dashboardId, userId, "published", dashboardreport.getReportId()))
                .orElse(null);
            if (publishedDashboardreport != null) {
                publishedDashboardreport.setOrderId(dashboardreport.getOrderId());
                publishedDashboardreport.setReportWidth(dashboardreport.getReportWidth());
                _reportDashboardRepository.save(publishedDashboardreport);
            } else {
                publishedDashboardreport =
                    dashboardversionMapper.dashboardversionreportEntityToDashboardversionreportEntity(
                        dashboardreport,
                        userId,
                        "published"
                    );
                _reportDashboardRepository.save(publishedDashboardreport);
            }

            _reportAppService.publishReport(dashboardreport.getUserId(), dashboardreport.getReportId());
        }

        //check if report is removed from running version
        List<DashboardversionreportEntity> dashboardPublishedreportList = _reportDashboardRepository.findByDashboardIdAndVersionAndUsersId(
            dashboardId,
            "published",
            userId
        );

        for (DashboardversionreportEntity dashboardeport : dashboardPublishedreportList) {
            DashboardversionreportEntity runningDashboardreport = _reportDashboardRepository
                .findById(new DashboardversionreportId(dashboardId, userId, "running", dashboardeport.getReportId()))
                .orElse(null);
            if (runningDashboardreport == null) {
                runningDashboardreport =
                    dashboardversionMapper.dashboardversionreportEntityToDashboardversionreportEntity(
                        dashboardeport,
                        userId,
                        "published"
                    );
                _reportDashboardRepository.delete(runningDashboardreport);
            }
        }

        return mapper.dashboardEntitiesToFindDashboardByIdOutput(foundDashboard, foundDashboardversion);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public FindDashboardByIdOutput refreshDashboard(Long userId, Long dashboardId) {
        DashboardEntity foundDashboard = _dashboardRepository.findById(dashboardId).orElse(null);

        if (
            foundDashboard != null && foundDashboard.getUsers() != null && foundDashboard.getUsers().getId() == userId
        ) {
            DashboardversionEntity ownerPublishedversion = _dashboardversionRepository
                .findById(new DashboardversionId(userId, dashboardId, "published"))
                .orElse(null);
            DashboardversionEntity ownerRunningversion = _dashboardversionRepository
                .findById(new DashboardversionId(userId, dashboardId, "running"))
                .orElse(null);

            UsersEntity foundUsers = _usersRepository.findById(userId).orElse(null);

            DashboardversionEntity updatedVersion = dashboardversionMapper.dashboardversionEntityToDashboardversionEntity(
                ownerPublishedversion,
                userId,
                "running"
            );
            updatedVersion.setUsers(foundUsers);
            updatedVersion.setVersiono(ownerRunningversion.getVersiono());
            updatedVersion.setIsRefreshed(true);
            _dashboardversionRepository.save(updatedVersion);

            List<DashboardversionreportEntity> dvrList = _reportDashboardRepository.findByDashboardIdAndVersionAndUsersId(
                dashboardId,
                "published",
                userId
            );
            for (DashboardversionreportEntity dvr : dvrList) {
                DashboardversionreportEntity updateDashboardreport = dashboardversionMapper.dashboardversionreportEntityToDashboardversionreportEntity(
                    dvr,
                    userId,
                    "running"
                );
                _reportDashboardRepository.save(updateDashboardreport);
                _reportAppService.refreshReport(userId, dvr.getReportId());
            }

            DashboardversionEntity runningversion = _dashboardversionRepository
                .findById(new DashboardversionId(userId, dashboardId, "running"))
                .orElse(null);

            FindDashboardByIdOutput output = mapper.dashboardEntitiesToFindDashboardByIdOutput(
                foundDashboard,
                runningversion
            );
            return output;
        }

        return null;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindDashboardByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<DashboardEntity> foundDashboard = _dashboardRepository.findAll(search(search), pageable);
        List<DashboardEntity> dashboardList = foundDashboard.getContent();
        Iterator<DashboardEntity> dashboardIterator = dashboardList.iterator();
        List<FindDashboardByIdOutput> output = new ArrayList<>();

        while (dashboardIterator.hasNext()) {
            DashboardEntity dashboard = dashboardIterator.next();
            DashboardversionEntity dashboardVersion = _dashboardversionRepository
                .findById(new DashboardversionId(dashboard.getUsers().getId(), dashboard.getId(), "running"))
                .orElse(null);
            FindDashboardByIdOutput dashboardOutput = mapper.dashboardEntitiesToFindDashboardByIdOutput(
                dashboard,
                dashboardVersion
            );
            DashboardversionEntity publishedversion = _dashboardversionRepository
                .findById(new DashboardversionId(dashboard.getUsers().getId(), dashboard.getId(), "published"))
                .orElse(null);
            output.add(dashboardOutput);
        }
        return output;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public FindDashboardByIdOutput addNewReportsToNewDashboard(AddNewReportToNewDashboardInput input) {
        CreateDashboardInput dashboardInput = mapper.addNewReportToNewDashboardInputTocreatDashboardInput(input);
        CreateDashboardOutput createdDashboard = create(dashboardInput);

        List<FindReportByIdOutput> reportsOutput = new ArrayList<>();
        List<CreateReportOutput> reportList = new ArrayList<>();

        for (CreateReportInput report : input.getReportDetails()) {
            report.setIsPublished(true);
            report.setOwnerId(createdDashboard.getOwnerId());
            CreateReportOutput createdReport = _reportAppService.create(report);
            if (report.getReportWidth() != null) {
                createdReport.setReportWidth(report.getReportWidth());
            } else createdReport.setReportWidth("mediumchart");
            reportList.add(createdReport);
            FindReportByIdOutput output = reportMapper.createReportOutputToFindReportByIdOutput(createdReport);
            output.setReportWidth(report.getReportWidth());
            reportsOutput.add(output);
        }

        _reportDashboardAppService.addReportsToDashboardRunningversion(createdDashboard, reportList);
        _reportDashboardAppService.addReportsToDashboardPublishedversion(createdDashboard, reportList);

        FindDashboardByIdOutput dashboardOuputDto = mapper.dashboardOutputToFindDashboardByIdOutput(createdDashboard);
        dashboardOuputDto.setReportDetails(reportsOutput);
        return dashboardOuputDto;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public FindDashboardByIdOutput addNewReportsToExistingDashboard(AddNewReportToExistingDashboardInput input) {
        DashboardversionEntity dashboardversion = _dashboardversionRepository
            .findById(new DashboardversionId(input.getOwnerId(), input.getId(), "running"))
            .orElse(null);
        DashboardEntity dashboard = _dashboardRepository.findById(input.getId()).orElse(null);

        CreateDashboardOutput createdDashboard = mapper.dashboardEntityAndDashboardversionEntityToCreateDashboardOutput(
            dashboard,
            dashboardversion
        );

        List<FindReportByIdOutput> reportsOutput = new ArrayList<>();
        List<CreateReportOutput> reportList = new ArrayList<>();

        for (CreateReportInput report : input.getReportDetails()) {
            report.setIsPublished(true);
            report.setOwnerId(createdDashboard.getOwnerId());
            CreateReportOutput createdReport = _reportAppService.create(report);
            if (report.getReportWidth() != null) {
                createdReport.setReportWidth(report.getReportWidth());
            } else createdReport.setReportWidth("mediumchart");

            reportList.add(createdReport);
            FindReportByIdOutput output = reportMapper.createReportOutputToFindReportByIdOutput(createdReport);
            output.setReportWidth(report.getReportWidth());
            reportsOutput.add(output);
        }

        _reportDashboardAppService.addReportsToDashboardRunningversion(createdDashboard, reportList);
        //	_reportDashboardAppService.addReportsToDashboardPublishedversion(createdDashboard, reportList);

        FindDashboardByIdOutput dashboardOuputDto = mapper.dashboardOutputToFindDashboardByIdOutput(createdDashboard);
        dashboardOuputDto.setReportDetails(reportsOutput);

        if ((dashboard.getUsers() != null && dashboard.getUsers().getId() == input.getOwnerId())) {
            dashboard.setIsPublished(false);
            _dashboardRepository.save(dashboard);
        }

        return dashboardOuputDto;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public FindDashboardByIdOutput addExistingReportsToNewDashboard(AddExistingReportToNewDashboardInput input) {
        List<FindReportByIdOutput> reportsOutput = new ArrayList<>();
        List<CreateReportOutput> reportList = new ArrayList<>();

        for (ExistingReportInput report : input.getReportDetails()) {
            ReportEntity reportEntity = _reportRepository.findById(report.getId()).orElse(null);
            ReportversionEntity reportversionEntity = _reportversionRepository
                .findById(new ReportversionId(input.getOwnerId(), report.getId(), "published"))
                .orElse(null);
            if (reportversionEntity == null) {
                reportversionEntity =
                    _reportversionRepository
                        .findById(new ReportversionId(input.getOwnerId(), report.getId(), "running"))
                        .orElse(null);
            }

            CreateReportOutput reportOutput = reportMapper.reportEntityAndReportversionEntityToCreateReportOutput(
                reportEntity,
                reportversionEntity
            );
            if (report.getReportWidth() != null) {
                reportOutput.setReportWidth(report.getReportWidth());
            } else reportOutput.setReportWidth("mediumchart");
            reportList.add(reportOutput);

            FindReportByIdOutput output = reportMapper.createReportOutputToFindReportByIdOutput(reportOutput);
            output.setReportWidth(reportOutput.getReportWidth());
            reportsOutput.add(output);
        }

        CreateDashboardInput dashboardInput = mapper.addExistingReportToNewDashboardInputTocreatDashboardInput(input);
        CreateDashboardOutput createdDashboard = create(dashboardInput);

        _reportDashboardAppService.addReportsToDashboardRunningversion(createdDashboard, reportList);
        _reportDashboardAppService.addReportsToDashboardPublishedversion(createdDashboard, reportList);

        FindDashboardByIdOutput dashboardOuputDto = mapper.dashboardOutputToFindDashboardByIdOutput(createdDashboard);
        dashboardOuputDto.setReportDetails(reportsOutput);

        return dashboardOuputDto;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public FindDashboardByIdOutput addExistingReportsToExistingDashboard(
        AddExistingReportToExistingDashboardInput input
    ) {
        DashboardEntity dashboard = _dashboardRepository.findById(input.getId()).orElse(null);
        DashboardversionEntity dashboardversion = _dashboardversionRepository
            .findById(new DashboardversionId(input.getOwnerId(), dashboard.getId(), "running"))
            .orElse(null);

        CreateDashboardOutput createdDashboard = mapper.dashboardEntityAndDashboardversionEntityToCreateDashboardOutput(
            dashboard,
            dashboardversion
        );

        List<FindReportByIdOutput> reportsOutput = new ArrayList<>();
        List<CreateReportOutput> reportList = new ArrayList<>();
        for (ExistingReportInput report : input.getReportDetails()) {
            ReportEntity reportEntity = _reportRepository.findById(report.getId()).orElse(null);
            ReportversionEntity reportversionEntity = _reportversionRepository
                .findById(new ReportversionId(input.getOwnerId(), reportEntity.getId(), "published"))
                .orElse(null);
            if (reportversionEntity == null) {
                reportversionEntity =
                    _reportversionRepository
                        .findById(new ReportversionId(input.getOwnerId(), reportEntity.getId(), "running"))
                        .orElse(null);
            }

            CreateReportOutput reportOutput = reportMapper.reportEntityAndReportversionEntityToCreateReportOutput(
                reportEntity,
                reportversionEntity
            );
            if (report.getReportWidth() != null) {
                reportOutput.setReportWidth(report.getReportWidth());
            } else reportOutput.setReportWidth("mediumchart");
            reportList.add(reportOutput);

            FindReportByIdOutput output = reportMapper.createReportOutputToFindReportByIdOutput(reportOutput);
            output.setReportWidth(reportOutput.getReportWidth());
            reportsOutput.add(output);
        }

        _reportDashboardAppService.addReportsToDashboardRunningversion(createdDashboard, reportList);

        FindDashboardByIdOutput dashboardOuputDto = mapper.dashboardOutputToFindDashboardByIdOutput(createdDashboard);
        dashboardOuputDto.setReportDetails(reportsOutput);

        if ((dashboard.getUsers() != null && dashboard.getUsers().getId() == input.getOwnerId())) {
            dashboard.setIsPublished(false);
            _dashboardRepository.save(dashboard);
        }

        return dashboardOuputDto;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QDashboardEntity dashboard = QDashboardEntity.dashboardEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(dashboard, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("userId") ||
                    list.get(i).replace("%20", "").trim().equals("description") ||
                    list.get(i).replace("%20", "").trim().equals("id") ||
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
        QDashboardEntity dashboard,
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
                    dashboard.isPublished.eq(Boolean.parseBoolean(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(dashboard.isPublished.ne(Boolean.parseBoolean(details.getValue().getSearchValue())));
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("ownerId")) {
                builder.and(dashboard.users.id.eq(Long.parseLong(joinCol.getValue())));
            }
        }
        return builder;
    }

    public Map<String, String> parseReportdashboardJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("dashboardId", keysString);
        return joinColumnMap;
    }
}
