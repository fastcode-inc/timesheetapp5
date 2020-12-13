package com.fastcode.timesheetapp5.application.core.status.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusInput {

    private String description;

    @NotNull(message = "id Should not be null")
    private Long id;

    private Long versiono;
}
