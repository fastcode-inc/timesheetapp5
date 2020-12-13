package com.fastcode.timesheetapp5.addons.reporting.domain.dashboard;

import com.fastcode.timesheetapp5.addons.reporting.domain.dashboardversion.DashboardversionEntity;
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
@Table(name = "dashboard")
public class DashboardEntity extends AbstractEntity {

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

    @OneToMany(mappedBy = "dashboard", cascade = CascadeType.ALL)
    private Set<DashboardversionEntity> dashboardversionSet = new HashSet<DashboardversionEntity>();

    public void addDashboardversion(DashboardversionEntity dashboardversion) {
        dashboardversionSet.add(dashboardversion);
        dashboardversion.setDashboard(this);
    }

    public void removeDashboardversion(DashboardversionEntity dashboardversion) {
        dashboardversionSet.remove(dashboardversion);
        dashboardversion.setDashboard(null);
    }
}
