package com.fastcode.timesheetapp5.application.core.customer.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindCustomerByIdOutput {

    private Long customerid;
    private String description;
    private Boolean isactive;
    private String name;
    private Long versiono;
}
