package com.fastcode.timesheetapp5.application.core.status.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStatusOutput {

    private String description;
    private Long id;
}
