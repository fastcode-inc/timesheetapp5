package com.fastcode.timesheetapp5.application.core.customer.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCustomerInput {

    @NotNull(message = "customerid Should not be null")
    private Long customerid;

    private String description;

    private Boolean isactive;

    private String name;

    private Long versiono;
}
