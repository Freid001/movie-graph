package com.movies.model.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;

@Builder
@Value
public class CreateActedIn {
    @NotEmpty(message = "Please provide a character name")
    public String character;
}
