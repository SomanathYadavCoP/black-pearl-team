package com.cop.hackathon.api.mom.service;

import com.cop.hackathon.api.mom.MomBotApiApplication;
import com.cop.hackathon.api.mom.model.MeetingInfo;
import com.cop.hackathon.api.mom.model.Participant;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Mail generator service.
 */
@Component
@Slf4j
public class MailGeneratorService {
    @Autowired
    private EmailService emailService;

    /**
     * Gets filled mail template.
     *
     * @param meetingInfo the meeting info
     * @return the filled mail template
     */
    public String getFilledMailTemplate(MeetingInfo meetingInfo) {
        log.info("Filling email template");
        StringBuilder finalEmailTemplate = new StringBuilder();
        Configuration cfg = new Configuration(new Version("2.3.23"));
        cfg.setClassForTemplateLoading(MomBotApiApplication.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");

        Map<String, Object> templateData = new HashMap<>();
        templateData.put("meetingInfo", meetingInfo);
        try {

            Template template = cfg.getTemplate("MomTemplate.html");


            try (StringWriter out = new StringWriter()) {

                template.process(templateData, out);
                finalEmailTemplate.append(out.getBuffer().toString());
                out.flush();
            }
        } catch (Exception e) {
            log.error("Error while processing email template", e);
        }

        return finalEmailTemplate.toString();
    }

    /**
     * Send email to participants.
     *
     * @param meetingInfo the meeting info
     * @param emailText   the email text
     */
    public void sendEmailToParticipants(MeetingInfo meetingInfo, String emailText) {
        log.info("Sending email to :{}", meetingInfo.getParticipants());
        for (Participant p : meetingInfo.getParticipants()) {
            emailService.sendSimpleMail(meetingInfo, emailText, p.getEmailId());
        }

        log.info("MoM sent to all participants!");
    }

}
