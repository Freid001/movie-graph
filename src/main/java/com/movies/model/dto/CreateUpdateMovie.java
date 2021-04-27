package com.movies.model.dto;

import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotEmpty;

@Data
@Value
public class CreateUpdateMovie {
    @NotEmpty(message = "Please provide a title")
    String title;
}
