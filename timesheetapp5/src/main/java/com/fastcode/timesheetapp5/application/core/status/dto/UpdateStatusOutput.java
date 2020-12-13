package com.fastcode.timesheetapp5.application.core.status.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusOutput {

    private String description;
    private Long id;
}
