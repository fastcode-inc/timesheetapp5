package com.fastcode.timesheetapp5.application.core.authorization.users.dto;

import java.time.*;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUsersInput {

    @Pattern(
        regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
        message = "Email Address should be valid"
    )
    private String emailaddress;

    private String firstname;

    private Boolean isactive = false;

    private Boolean isemailconfirmed;

    private String lastname;

    private String password;

    private String username;
}
