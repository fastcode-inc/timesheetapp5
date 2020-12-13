package com.fastcode.timesheetapp5.addons.reporting.application.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUsersOutput {

    private String emailaddress;
    private String firstname;
    private Long id;
    private Boolean isactive;
    private Boolean isemailconfirmed;
    private String lastname;
    private String username;
    private Long dashboardId;
}
