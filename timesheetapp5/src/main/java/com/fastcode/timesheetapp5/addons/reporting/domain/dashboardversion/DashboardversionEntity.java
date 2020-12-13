package com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversion;

import com.fastcode.timesheetapp5.addons.reporting.domain.dashboard.DashboardEntity;
import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversionreport.DashboardversionreportEntity;
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
@Table(name = "dashboardversion")
@IdClass(DashboardversionId.class)
public class DashboardversionEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "dashboard_id", nullable = false)
    private Long dashboardId;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "dashboard_version", nullable = false, length = 255)
    private String dashboardVersion;

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @Basic
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Basic
    @Column(name = "is_refreshed", nullable = true)
    private Boolean isRefreshed;

    @ManyToOne
    @JoinColumn(name = "dashboard_id", insertable = false, updatable = false)
    private DashboardEntity dashboard;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UsersEntity users;

    @OneToMany(mappedBy = "dashboardversionEntity", cascade = CascadeType.ALL)
    private Set<DashboardversionreportEntity> dashboardversionreportSet = new HashSet<DashboardversionreportEntity>();

    public void addDashboardversionreport(DashboardversionreportEntity dashboardversionreport) {
        dashboardversionreportSet.add(dashboardversionreport);
        dashboardversionreport.setDashboardversionEntity(this);
    }

    public void removeDashboardversionreport(DashboardversionreportEntity dashboardversionreport) {
        dashboardversionreportSet.remove(dashboardversionreport);
        dashboardversionreport.setDashboardversionEntity(null);
    }
}
