package com.fastcode.timesheetapp5.addons.reporting.domain.report;

import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversionreport.DashboardversionreportEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.reportversion.ReportversionEntity;
import com.fastcode.timesheetapp5.domain.core.abstractentity.AbstractEntity;
import com.fastcode.timesheetapp5.domain.core.authorization.users.UsersEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "report")
public class ReportEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "is_published", nullable = false)
    private Boolean isPublished;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UsersEntity users;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
    private Set<ReportversionEntity> reportversionSet = new HashSet<ReportversionEntity>();

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
    private Set<DashboardversionreportEntity> reportdashboardSet = new HashSet<DashboardversionreportEntity>();

    public void addReportversion(ReportversionEntity reportVersion) {
        reportversionSet.add(reportVersion);
        reportVersion.setReport(this);
    }

    public void removeReportversion(ReportversionEntity reportVersion) {
        reportversionSet.remove(reportVersion);
        reportVersion.setReport(null);
    }

    public void addDashboardversionreport(DashboardversionreportEntity dashboardversionreport) {
        reportdashboardSet.add(dashboardversionreport);
        dashboardversionreport.setReport(this);
    }

    public void removeDashboardversionreport(DashboardversionreportEntity dashboardversionreport) {
        reportdashboardSet.remove(dashboardversionreport);
        dashboardversionreport.setReport(null);
    }
}
