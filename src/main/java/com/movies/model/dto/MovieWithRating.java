package com.movies.model.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class MovieWithRating {
    String movieId;

    String title;

    Double rating;

    Integer reviews;
}
