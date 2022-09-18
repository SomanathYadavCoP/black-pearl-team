package com.cop.hackathon.api.mom.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SummaryRequest {
    List<String> text = new ArrayList<>();
}
