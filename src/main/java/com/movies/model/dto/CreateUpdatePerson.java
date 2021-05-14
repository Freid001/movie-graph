package com.movies.model.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;

@Builder
@Value
public class CreateUpdatePerson {
    @NotEmpty(message = "Please provide a first name")
    String firstName;

    @NotEmpty(message = "Please provide a last name")
    String lastName;
}
