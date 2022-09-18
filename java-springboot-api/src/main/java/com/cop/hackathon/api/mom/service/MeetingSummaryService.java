package com.cop.hackathon.api.mom.service;

import com.cop.hackathon.api.mom.model.MeetingInfo;
import com.cop.hackathon.api.mom.model.Participant;
import com.cop.hackathon.api.mom.model.SummaryRequest;
import com.cop.hackathon.api.mom.model.SummaryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MeetingSummaryService {
    @Autowired
    MailGeneratorService mailGeneratorService;

    @Value("${python.summary.api.url}")
    private String pythonServerURL;


    @Async("asyncExecutor")
    public void generateMeetingSummaryAndSendMoMEmail(MeetingInfo meetingInfo) {
        String mailTemplateFilled = null;
        try {

            //Enrich the list
            List<Participant> participantList = meetingInfo.getParticipants();
            List<Participant> attendeeList = meetingInfo.getAttendees();
            List<Participant> apologiesList = participantList.stream().filter(e -> !attendeeList.contains(e)).collect(Collectors.toList());
            meetingInfo.setApologies(apologiesList);

            getMeetingSummaryFromPythonAPI(meetingInfo);

            List<String> summaryPoints = meetingInfo.getSummaryPoints();


            List<String> actionItems = meetingInfo.getActionItems();

            meetingInfo.setActionItems(actionItems);

            List<String> finalSummaryPoints = summaryPoints.stream().filter(e -> !actionItems.contains(e)).collect(Collectors.toList());

            meetingInfo.setFinalSummaryPoints(finalSummaryPoints);

            mailTemplateFilled = mailGeneratorService.getFilledMailTemplate(meetingInfo);


        } catch (Exception e) {
            log.error("Error while processing request", e);
            mailTemplateFilled = "MoM Bot is facing issues while generating the minutes of meeting!";

        } finally {
            log.info("Mail Template data:{}", mailTemplateFilled);
            mailGeneratorService.sendEmailToParticipants(meetingInfo, mailTemplateFilled);
        }

    }

    public void getMeetingSummaryFromPythonAPI(MeetingInfo meetingInfo) throws Exception {
        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        SummaryRequest summaryRequest = new SummaryRequest();
        String[] lines = meetingInfo.getTranscript().split("\\n");
        log.info("No of lines in transcript:{}", lines.length);
        List<String> text = Arrays.asList(lines);
        summaryRequest.setText(text);
        HttpEntity<SummaryRequest> entity = new HttpEntity<>(summaryRequest, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> summaryResponseEntity = restTemplate.postForEntity(pythonServerURL, entity, String.class);

        log.info("got response summary from python API:{}", summaryResponseEntity.getBody());
        ObjectMapper objectMapper = new ObjectMapper();
        SummaryResponse summaryResponse = objectMapper.readValue(summaryResponseEntity.getBody(), SummaryResponse.class);

        log.info("got response action items from python API:{}", summaryResponse.getActionItems());

        meetingInfo.setActionItems(summaryResponse.getActionItems());
        meetingInfo.setSummaryPoints(summaryResponse.getSummary());

    }
}
