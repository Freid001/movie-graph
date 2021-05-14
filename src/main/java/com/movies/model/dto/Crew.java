package com.movies.model.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Crew {
    String firstName;

    String lastName;

    String role;
}
