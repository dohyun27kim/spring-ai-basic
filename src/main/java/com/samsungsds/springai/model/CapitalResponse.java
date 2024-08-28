package com.samsungsds.springai.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record CapitalResponse(@JsonPropertyDescription("This is the city name") String answer) {
}