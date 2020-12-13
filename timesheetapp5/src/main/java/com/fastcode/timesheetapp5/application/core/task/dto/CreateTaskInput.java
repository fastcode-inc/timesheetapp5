package com.fastcode.timesheetapp5.application.core.task.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTaskInput {

    private String description;

    private Boolean isactive;

    private String name;

    private Long projectid;
}
