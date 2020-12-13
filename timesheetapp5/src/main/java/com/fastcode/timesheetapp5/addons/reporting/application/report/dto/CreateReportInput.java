package com.fastcode.timesheetapp5.addons.reporting.application.report.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.json.simple.JSONObject;

@Getter
@Setter
public class CreateReportInput {

    private Long ownerId;
    private Boolean isPublished;
    private String ctype;
    private String description;
    private JSONObject query;

    @Length(max = 255, message = "reportType must be less than 255 characters")
    private String reportType;

    @NotNull
    private String title;

    private String reportWidth;
}
