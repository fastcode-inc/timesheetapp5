package com.fastcode.timesheetapp5.application.core.project.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectOutput {

    private String description;
    private LocalDateTime enddate;
    private Long id;
    private String name;
    private LocalDateTime startdate;
    private Long customerid;
    private Long customerDescriptiveField;
}
