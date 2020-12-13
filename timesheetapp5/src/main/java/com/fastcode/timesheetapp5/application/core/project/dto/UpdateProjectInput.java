package com.fastcode.timesheetapp5.application.core.project.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProjectInput {

    private String description;

    private LocalDateTime enddate;

    @NotNull(message = "id Should not be null")
    private Long id;

    @NotNull(message = "name Should not be null")
    private String name;

    private LocalDateTime startdate;

    private Long customerid;
    private Long versiono;
}
