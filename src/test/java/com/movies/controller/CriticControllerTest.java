package com.movies.controller;

import com.movies.Mocks;
import com.movies.exception.NotFoundException;
import com.movies.model.dto.CreateActedIn;
import com.movies.model.dto.CreateReviewed;
import com.movies.model.dto.CreateUpdatePerson;
import com.movies.model.node.Person;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;

public class CriticControllerTest {
    String personId = "75880b53-c8ec-46a6-a8b6-883e0a501ab2";
    String firstName = "Roger";
    String lastName = "Ebert";
    String movieId = "753a078b-e2ef-4304-8aab-f5086bf39d02";
    String title = "Star Wars: The Empire Strikes Back";
    Integer rating = 4;

    @Test
    public void testCanCreateReviewedRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById(movieId, title);
        mocks.savePerson(personId, firstName, lastName);

        CriticController criticController = new CriticController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        ResponseEntity<Person> r = criticController.postReviewed(personId, movieId,
                CreateReviewed.builder().rating(rating).build()
        );

        assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(r.getBody()).isNotNull();

        assertThat(r.getBody().getReviewed().stream().filter(x -> {
            return x.getMovie().getMovieId().equals(movieId);
        })).hasSize(1);
    }

    @Test
    public void testCantFindMovieWhenCreatingReviewedRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById("", "");

        CriticController criticController = new CriticController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            criticController.postReviewed(personId, "",
                    CreateReviewed.builder().rating(rating).build());
        });

        assertThat(exception.getMessage()).isEqualTo("Movie not found!");
    }

    @Test
    public void testCantFindPersonWhenCreatingReviewedRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById("", "", "");
        mocks.findMovieById(movieId, title);

        CriticController criticController = new CriticController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            criticController.postReviewed("", movieId,
                    CreateReviewed.builder().rating(rating).build());
        });

        assertThat(exception.getMessage()).isEqualTo("Person not found!");
    }

    @Test
    public void testCanDeleteReviewedRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById(movieId, title);
        mocks.savePerson(personId, firstName, lastName);

        CriticController criticController = new CriticController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        ResponseEntity<Person> r = criticController.deleteReviewed(personId, movieId);

        assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(r.getBody()).isNotNull();

        assertThat(r.getBody().getReviewed().stream().filter(x -> {
            return x.getMovie().getMovieId().equals(movieId);
        })).hasSize(0);
    }

    @Test
    public void testCantFindMovieWhenDeletingReviewedRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById("", "");

        CriticController criticController = new CriticController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            criticController.deleteReviewed(personId, "");
        });

        assertThat(exception.getMessage()).isEqualTo("Movie not found!");
    }

    @Test
    public void testCantFindPersonWhenDeletingActedInRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById("", "", "");
        mocks.findMovieById(movieId, title);

        CriticController criticController = new CriticController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            criticController.deleteReviewed("", movieId);
        });

        assertThat(exception.getMessage()).isEqualTo("Person not found!");
    }
}
