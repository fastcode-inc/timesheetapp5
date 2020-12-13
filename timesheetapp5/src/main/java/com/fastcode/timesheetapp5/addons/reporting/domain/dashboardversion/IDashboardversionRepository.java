package com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversion;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository("dashboardversionRepository")
public interface IDashboardversionRepository
    extends
        JpaRepository<DashboardversionEntity, DashboardversionId>, QuerydslPredicateExecutor<DashboardversionEntity> {
    @Query("select r from DashboardversionEntity r where r.id = ?1 and r.users.id=?2 ")
    DashboardversionEntity findByDashboardversionIdAndUsersId(Long dashboardversionId, Long userId);

    @Query("select r from DashboardversionEntity r where r.users.id=?1 ")
    List<DashboardversionEntity> findByUsersId(Long id);
}
