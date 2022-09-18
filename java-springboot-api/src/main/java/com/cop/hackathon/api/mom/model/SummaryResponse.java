package com.cop.hackathon.api.mom.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SummaryResponse {
    List<String> actionItems = new ArrayList<>();
    List<String> summary = new ArrayList<>();

}
