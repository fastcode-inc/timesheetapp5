package com.fastcode.timesheetapp5.domain.core.project;

import com.fastcode.timesheetapp5.domain.core.abstractentity.AbstractEntity;
import com.fastcode.timesheetapp5.domain.core.customer.CustomerEntity;
import java.time.*;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "project")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class ProjectEntity extends AbstractEntity {

    @Basic
    @Column(name = "enddate", nullable = true)
    private LocalDateTime enddate;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "description", nullable = true)
    private String description;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "startdate", nullable = true)
    private LocalDateTime startdate;

    @ManyToOne
    @JoinColumn(name = "customerid")
    private CustomerEntity customer;
}
