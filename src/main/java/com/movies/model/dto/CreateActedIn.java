package com.movies.model.dto;

import javax.validation.constraints.NotEmpty;

public class CreateActedIn {
    @NotEmpty(message = "Please provide a character name")
    public String character;
}
