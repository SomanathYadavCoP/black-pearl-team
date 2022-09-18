package com.cop.hackathon.api.mom.service;

import com.cop.hackathon.api.mom.model.MeetingInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * The type Email service.
 */
@Component
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    /**
     * Send simple mail.
     *
     * @param meetingInfo    the meeting info
     * @param emailBody      the email body
     * @param recipientEmail the recipient email
     */
    public void sendSimpleMail(MeetingInfo meetingInfo, String emailBody, String recipientEmail) {

        // Try block to check for exceptions
        try {
            MimeMessage mimeMessage
                    = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("MoM Bot <" + sender + ">");
            mimeMessageHelper.setTo(recipientEmail);
            mimeMessageHelper.setText(emailBody, true);
            mimeMessageHelper.setSubject("Minutes of Meeting For:" + meetingInfo.getSubject());
            // Sending the mail
            javaMailSender.send(mimeMessage);
            log.info("Mail Sent Successfully...");
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            log.error("Error while Sending Mail", e);

        }
    }
}
