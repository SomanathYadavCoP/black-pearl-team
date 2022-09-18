package com.cop.hackathon.api.mom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingInfo {
    //input date
    private String subject;
    private String meetingDate;
    private String location;

    private String startTime;
    private String endTime;
    private List<Participant> participants = new ArrayList<>();
    private List<Participant> attendees = new ArrayList<>();
    private String transcript;

    //Enriched data
    @JsonIgnore
    private List<Participant> apologies = new ArrayList<>(); //Computed as participants - attendees
    @JsonIgnore
    private List<String> summaryPoints =  new ArrayList<>(); // Computed for every 200 words in the transcript
    @JsonIgnore
    private List<String> finalSummaryPoints = new ArrayList<>(); // Summary points - action items
    @JsonIgnore
    private List<String> actionItems = new ArrayList<>(); // Computed from summaryPoints by looking for some promise and future words

}