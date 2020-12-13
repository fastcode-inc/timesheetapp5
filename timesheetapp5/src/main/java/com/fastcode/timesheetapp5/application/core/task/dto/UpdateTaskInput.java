package com.fastcode.timesheetapp5.application.core.task.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskInput {

    private String description;

    @NotNull(message = "id Should not be null")
    private Long id;

    private Boolean isactive;

    private String name;

    private Long projectid;
    private Long versiono;
}
