package com.fastcode.timesheetapp5.application.core.authorization.users.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUsersOutput {

    private String emailaddress;
    private String firstname;
    private Long id;
    private Boolean isactive;
    private Boolean isemailconfirmed;
    private String lastname;
    private String username;
    private String theme;
    private String language;
}
