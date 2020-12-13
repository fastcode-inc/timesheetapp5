package com.fastcode.timesheetapp5.addons.reporting.domain.report;

import com.fastcode.timesheetapp5.addons.reporting.application.report.dto.ReportDetailsOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReportRepositoryCustom {
    Page<ReportDetailsOutput> getAllReportsByUsersId(Long userId, String search, Pageable pageable) throws Exception;
}
