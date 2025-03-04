package com.fastcode.timesheetapp5.application.core.authorization.users.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserspreferenceOutput {

    private String firstname;
    private Boolean isactive;
    private String emailaddress;
    private String lastname;
    private String password;
    private Boolean isemailconfirmed;
    private Long id;
    private String username;
    private Long usersId;
}
