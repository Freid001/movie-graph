package com.movies.model.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;

@Builder
@Value
public class CreateUpdateMovie {
    @NotEmpty(message = "Please provide a title")
    String title;
}
