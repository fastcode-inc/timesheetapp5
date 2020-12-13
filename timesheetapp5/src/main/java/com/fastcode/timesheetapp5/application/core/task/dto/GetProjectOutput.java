package com.fastcode.timesheetapp5.application.core.task.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetProjectOutput {

    private String description;
    private LocalDateTime enddate;
    private Long id;
    private String name;
    private LocalDateTime startdate;
    private Long taskId;
}
