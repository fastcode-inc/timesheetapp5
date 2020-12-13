package com.fastcode.timesheetapp5.application.core.usertask.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUsertaskOutput {

    private Long id;
    private Long taskid;
    private Long taskDescriptiveField;
    private Long userid;
    private Long usersDescriptiveField;
}
