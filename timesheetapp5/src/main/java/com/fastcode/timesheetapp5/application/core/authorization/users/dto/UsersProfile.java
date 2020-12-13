package com.fastcode.timesheetapp5.application.core.authorization.users.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersProfile {

    @NotNull(message = "username Should not be null")
    private String username;

    @NotNull(message = "emailaddress Should not be null")
    @Pattern(
        regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
        message = "Emailaddress should be valid"
    )
    private String emailaddress;

    @NotNull(message = "firstname Should not be null")
    private String firstname;

    @NotNull(message = "lastname Should not be null")
    private String lastname;

    private String theme;
    private String language;
}
