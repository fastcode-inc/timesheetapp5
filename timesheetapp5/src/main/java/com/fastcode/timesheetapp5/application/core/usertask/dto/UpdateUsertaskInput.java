package com.fastcode.timesheetapp5.application.core.usertask.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUsertaskInput {

    @NotNull(message = "id Should not be null")
    private Long id;

    private Long taskid;
    private Long userid;
    private Long versiono;
}
