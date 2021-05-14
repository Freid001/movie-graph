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

public class ActorControllerTest {
    String personId = "c9874f18-3c0b-4c92-87fb-8b18e5676e31";
    String firstName = "Harrison";
    String lastName = "Ford";
    String movieId = "a49ebaab-88b5-4847-9f9d-915805367625";
    String title = "Star Wars: A New Hope";
    String character = "Han Solo";

    @Test
    public void testCanCreateActedInRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById(movieId, title);
        mocks.savePerson(personId, firstName, lastName);

        ActorController actorController = new ActorController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        ResponseEntity<Person> r = actorController.postActedIn(personId, movieId,
                CreateActedIn.builder().character(character).build()
        );

        assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(r.getBody()).isNotNull();

        assertThat(r.getBody().getActedIn().stream().filter(x -> {
            return x.getMovie().getMovieId().equals(movieId);
        })).hasSize(1);
    }

    @Test
    public void testCantFindMovieWhenCreatingActedInRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById("", "");

        ActorController actorController = new ActorController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            actorController.postActedIn(personId, "",
                    CreateActedIn.builder().character(character).build());
        });

        assertThat(exception.getMessage()).isEqualTo("Movie not found!");
    }

    @Test
    public void testCantFindPersonWhenCreatingActedInRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById("", "", "");
        mocks.findMovieById(movieId, title);

        ActorController actorController = new ActorController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            actorController.postActedIn("", movieId,
                    CreateActedIn.builder().character(character).build());
        });

        assertThat(exception.getMessage()).isEqualTo("Person not found!");
    }

    @Test
    public void testCanDeleteActedInRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById(movieId, title);
        mocks.savePerson(personId, firstName, lastName);

        ActorController actorController = new ActorController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        ResponseEntity<Person> r = actorController.deleteActedIn(personId, movieId);

        assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(r.getBody()).isNotNull();

        assertThat(r.getBody().getActedIn().stream().filter(x -> {
            return x.getMovie().getMovieId().equals(movieId);
        })).hasSize(0);
    }

    @Test
    public void testCantFindMovieWhenDeletingActedInRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById("", "");

        ActorController actorController = new ActorController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
                    actorController.deleteActedIn(personId, "");
        });

        assertThat(exception.getMessage()).isEqualTo("Movie not found!");
    }

    @Test
    public void testCantFindPersonWhenDeletingActedInRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById("", "", "");
        mocks.findMovieById(movieId, title);

        ActorController actorController = new ActorController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            actorController.deleteActedIn("", movieId);
        });

        assertThat(exception.getMessage()).isEqualTo("Person not found!");
    }
}
