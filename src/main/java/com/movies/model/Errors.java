package com.movies.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class Errors {
    Integer code;

    String message;

    List<String> details;
}
