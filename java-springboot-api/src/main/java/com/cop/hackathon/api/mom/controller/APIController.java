package com.cop.hackathon.api.mom.controller;

import com.cop.hackathon.api.mom.model.MeetingInfo;
import com.cop.hackathon.api.mom.service.MeetingSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Api controller.
 */
@RestController
@Slf4j
public class APIController {
    /**
     * The Mail generator service.
     */

    @Autowired
    MeetingSummaryService meetingSummaryService;

    /**
     * Root string.
     *
     * @return the string
     */
    @GetMapping("/api")
    public String root() {
        return "Use proper API URL to post the transcript!";
    }

    /**
     * Ping service string.
     *
     * @return the string
     */
    @GetMapping("/apistatus")
    public String pingService() {
        return "Meeting Bot API is running!";
    }

    /**
     * Submit meeting transcript for mom response entity.
     *
     * @param meetingInfo the meeting info
     * @return the response entity
     */
    @PostMapping("/api/submit-transcript-api")
    public ResponseEntity<String> submitMeetingTranscriptForMom(@RequestBody MeetingInfo meetingInfo) {
    log.info("Got meeting request:{}" , meetingInfo);
        ResponseEntity<String> apiResponse = null;
        try {

            meetingSummaryService.generateMeetingSummaryAndSendMoMEmail(meetingInfo);

            apiResponse = new ResponseEntity<>(
                    "Request accepted and is under processing, you will receive MOM email!", HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error while processing request", e);
            apiResponse = new ResponseEntity<>(
                    "Error while processing request!", HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return apiResponse;
    }
}
