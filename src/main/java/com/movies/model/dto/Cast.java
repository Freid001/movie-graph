package com.movies.model.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Cast {
    String firstName;

    String lastName;

    String character;
}
