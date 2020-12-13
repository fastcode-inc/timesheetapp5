package com.fastcode.timesheetapp5.application.core.authorization.users.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUsersInput {

    @Pattern(
        regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
        message = "Email Address should be valid"
    )
    @NotNull(message = "emailaddress Should not be null")
    private String emailaddress;

    @NotNull(message = "firstname Should not be null")
    private String firstname;

    @NotNull(message = "id Should not be null")
    private Long id;

    @NotNull(message = "isactive Should not be null")
    private Boolean isactive = false;

    @NotNull(message = "isemailconfirmed Should not be null")
    private Boolean isemailconfirmed;

    @NotNull(message = "lastname Should not be null")
    private String lastname;

    private String password;

    @NotNull(message = "username Should not be null")
    private String username;

    private Long versiono;
}
