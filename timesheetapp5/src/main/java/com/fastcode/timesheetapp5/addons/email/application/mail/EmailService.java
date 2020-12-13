package com.fastcode.timesheetapp5.addons.email.application.mail;

import com.fastcode.timesheetapp5.addons.docmgmt.domain.file.FileEntity;
import com.fastcode.timesheetapp5.addons.docmgmt.domain.file.IFileContentStore;
import com.fastcode.timesheetapp5.addons.docmgmt.domain.file.IFileRepository;
import com.fastcode.timesheetapp5.addons.email.application.mail.dto.CreateEmailInput;
import com.fastcode.timesheetapp5.addons.email.domain.emailattachments.EmailAttachments;
import com.fastcode.timesheetapp5.addons.email.domain.emailattachments.EmailAttachmentsRepository;
import com.fastcode.timesheetapp5.addons.email.domain.emailhistory.EmailHistory;
import com.fastcode.timesheetapp5.addons.email.domain.emailhistory.EmailHistoryRepository;
import com.fastcode.timesheetapp5.commons.logging.LoggingHelper;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.mail.internet.MimeMessage;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailService implements IEmailService {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    public LoggingHelper logHelper;

    @Autowired
    private EmailAttachmentsRepository emailAttachmentsRepository;

    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    @Autowired
    private IFileContentStore contentStore;

    @Autowired
    @Qualifier("fileRepository")
    private IFileRepository filesRepo;

    @Async
    public void sendEmail(SimpleMailMessage email) {
        try {
            emailSender.send(email);
        } catch (Exception e) {
            logHelper.getLogger().error(e.getMessage());
        }
    }

    public SimpleMailMessage buildEmail(String email, String subject, String emailText) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(email);
        emailMessage.setSubject(subject);
        emailMessage.setText(emailText);

        return emailMessage;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void sendMessage(CreateEmailInput email) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
            );

            String[] toArray = email.getTo().split(",", -1);
            String[] ccArray = null;
            String[] bccArray = null;

            if ((email.getCc() != null && !email.getCc().trim().isEmpty())) {
                ccArray = email.getCc().split(",", -1);
                helper.setCc(ccArray);
            }

            if ((email.getBcc() != null && !email.getBcc().trim().isEmpty())) {
                bccArray = email.getBcc().split(",", -1);
                helper.setBcc(bccArray);
            }

            helper.setTo(toArray);
            helper.setSubject(email.getSubject());
            helper.setText(email.getEmailBody(), email.getIsHtml()); // Use the true flag to indicate the text included is HTML

            helper.setSentDate(new Date());
            helper.setReplyTo(email.getReplyTo());

            // The Email body is expected to have inline images in the following format:
            // <img src='cid:[imageFileName.extension]'>
            // When we create a File entity, it's name should also be [imageFileName.extension]
            // We need to ensure that all images in the message have different file names

            for (Long fileId : email.getInlineImages()) {
                Optional<FileEntity> file = filesRepo.findById(fileId);
                if (file.isPresent()) {
                    ByteArrayResource fileStreamResource = getFileStreamResource(file.get().getId());
                    if (fileStreamResource != null) helper.addInline(
                        file.get().getName(),
                        fileStreamResource,
                        file.get().getMimeType()
                    );
                }
            }

            // Now add the real attachments
            for (Long fileId : email.getAttachments()) {
                Optional<FileEntity> file = filesRepo.findById(fileId);
                if (file.isPresent()) {
                    ByteArrayResource fileStreamResource = getFileStreamResource(file.get().getId());
                    if (fileStreamResource != null) helper.addAttachment(file.get().getName(), fileStreamResource);
                }
            }

            emailSender.send(message);
            saveHistory(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveHistory(CreateEmailInput email) {
        EmailHistory emailHistory = new EmailHistory();
        emailHistory.setTo(email.getTo());
        emailHistory.setCc(email.getCc());
        emailHistory.setBcc(email.getBcc());
        emailHistory.setSubject(email.getSubject());
        emailHistory.setBody(email.getEmailBody());
        emailHistory.setReplyTo(email.getReplyTo());
        emailHistory.setSentDate(new Date());
        emailHistory.setIsHtml(true);
        emailHistory = emailHistoryRepository.save(emailHistory);

        try {
            saveHistoryFiles("InlineImage", emailHistory, email);
            saveHistoryFiles("Attachment", emailHistory, email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveHistoryFiles(String filesType, EmailHistory emailHistory, CreateEmailInput email)
        throws Exception {
        List<Long> files = null;

        if (filesType.equalsIgnoreCase(("InlineImage"))) {
            files = email.getInlineImages();
        } else if (filesType.equalsIgnoreCase("Attachment")) {
            files = email.getAttachments();
        } else {
            throw new Exception("The specified file type is not supported");
        }

        for (Long f : files) {
            EmailAttachments emailAttachments = new EmailAttachments();
            emailAttachments.setEmailId(emailHistory.getId());
            emailAttachments.setEmailHistory(emailHistory);
            Optional<FileEntity> file = filesRepo.findById(f);

            if (filesType.equalsIgnoreCase("Attachment")) {
                emailAttachments.setType("Attachment");
            } else if (filesType.equalsIgnoreCase("InlineImage")) {
                emailAttachments.setType("InlineImage");
            } else {
                throw new Exception("The specified file type is not supported");
            }

            emailAttachments.setFile(file.get());
            emailAttachments.setFileId(f);

            emailAttachmentsRepository.save(emailAttachments);
        }
    }

    public ByteArrayResource getFileStreamResource(Long fileId) {
        try {
            Optional<FileEntity> f = filesRepo.findById(fileId);
            InputStream content = contentStore.getContent(f.get());
            return content != null ? new ByteArrayResource(IOUtils.toByteArray(content)) : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
