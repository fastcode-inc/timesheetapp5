package com.fastcode.timesheetapp5.addons.email.application.mail.dto;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateEmailInput {

    @NotNull(message = "To: should not be null")
    private String to;

    private String cc;
    private String bcc;
    private String subject;
    private String emailBody;
    private String replyTo;
    private Date sentDate;
    private Boolean isHtml;

    //List of fileIds for inline images and attachments
    private List<Long> inlineImages;
    private List<Long> attachments;
}
