package com.fastcode.timesheetapp5.addons.reporting.application.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetRoleOutput {

    private Long id;
    private String displayName;
    private String name;
    private Long dashboardId;
}
