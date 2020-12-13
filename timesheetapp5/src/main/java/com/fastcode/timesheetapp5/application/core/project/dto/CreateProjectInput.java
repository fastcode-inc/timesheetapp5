package com.fastcode.timesheetapp5.application.core.project.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectInput {

    private String description;

    private LocalDateTime enddate;

    @NotNull(message = "name Should not be null")
    private String name;

    private LocalDateTime startdate;

    private Long customerid;
}
