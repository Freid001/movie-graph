package com.movies.controller;

import com.movies.Mocks;
import com.movies.exception.NotFoundException;
import com.movies.model.dto.CreateUpdateMovie;
import com.movies.model.node.Movie;
import com.movies.model.dto.MovieWithRating;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MovieControllerTest {
    String id = "52a8c394-114a-4740-943c-9ea38dcb181a";
    String title = "Star Wars: A New Hope";
    Double rating = 6.0;
    Integer reviews = 1;

    @Test
    public void testCanRetrieveMovie() {
        Mocks mocks = new Mocks();
        mocks.findMovieByIdWithRating(id, title, rating, reviews);

        MovieController movieController = new MovieController(
                mocks.getModelMapper(),
                mocks.getMovieService()
        );

        ResponseEntity<MovieWithRating> r = movieController.getMovie(anyString());

        assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(r.getBody()).isNotNull();
        assertThat(r.getBody().getMovieId()).isEqualTo(id);
        assertThat(r.getBody().getTitle()).isEqualTo(title);
        assertThat(r.getBody().getRating()).isEqualTo(rating);
        assertThat(r.getBody().getReviews()).isEqualTo(reviews);
    }

    @Test
    public void testCantFindMovie() {
        Mocks mocks = new Mocks();
        mocks.findMovieById("", "");

        MovieController movieController = new MovieController(
                mocks.getModelMapper(),
                mocks.getMovieService()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            movieController.getMovie(anyString());
        });

        assertThat(exception.getMessage()).isEqualTo("Movie not found!");
    }

    @Test
    public void testCanCreateMovie() {
        Mocks mocks = new Mocks();
        mocks.saveMovie(id, title);

        MovieController movieController = new MovieController(
                mocks.getModelMapper(),
                mocks.getMovieService()
        );

        ResponseEntity<Movie> r = movieController.postMovie(CreateUpdateMovie.builder().title(title).build());

        assertThat(r.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(r.getBody()).isNotNull();
        assertThat(r.getBody().getMovieId()).isEqualTo(id);
        assertThat(r.getBody().getTitle()).isEqualTo(title);
    }
}
