package com.movies.model.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
@Value
public class CreateReviewed {
    @NotNull(message = "Please provide a rating")
    @Min(value = 0, message = "Rating should not be less than 0")
    @Max(value = 10, message = "Rating should not be greater than 10")
    public Integer rating;
}
