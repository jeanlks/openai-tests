package com.test.openai.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record CapitalResponse(@JsonPropertyDescription("This is the city name") String answer,
                              @JsonPropertyDescription("This is the city population") String population,
                              @JsonPropertyDescription("This is the city currency") String currency) {
}
