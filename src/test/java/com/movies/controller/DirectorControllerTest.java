package com.movies.controller;

import com.movies.Mocks;
import com.movies.exception.NotFoundException;
import com.movies.model.dto.CreateUpdatePerson;
import com.movies.model.node.Person;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;

public class DirectorControllerTest {
    String personId = "4060533e-f01c-4da1-afeb-a8d8c4655836";
    String firstName = "Steven";
    String lastName = "Spielberg";
    String movieId = "9d2ff2d2-d7c5-4ef7-a5fd-196ed617c4cd";
    String title = "Indiana Jones raiders of the Lost Ark";

    @Test
    public void testCanCreateDirectedRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById(movieId, title);
        mocks.savePerson(personId, firstName, lastName);

        DirectorController directorController = new DirectorController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        ResponseEntity<Person> r = directorController.postDirected(personId, movieId);

        assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(r.getBody()).isNotNull();

        assertThat(r.getBody().getDirected().stream().filter(x -> {
            return x.getMovie().getMovieId().equals(movieId);
        })).hasSize(1);
    }

    @Test
    public void testCantFindMovieWhenCreatingDirectedRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById("", "");

        DirectorController directorController = new DirectorController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            directorController.postDirected(personId, "");
        });

        assertThat(exception.getMessage()).isEqualTo("Movie not found!");
    }

    @Test
    public void testCantFindPersonWhenCreatingDirectedRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById("", "", "");
        mocks.findMovieById(movieId, title);

        DirectorController directorController = new DirectorController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            directorController.postDirected("", movieId);
        });

        assertThat(exception.getMessage()).isEqualTo("Person not found!");
    }

    @Test
    public void testCanDeleteDirectedRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById(movieId, title);
        mocks.savePerson(personId, firstName, lastName);

        DirectorController directorController = new DirectorController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        ResponseEntity<Person> r = directorController.deleteDirected(personId, movieId);

        assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(r.getBody()).isNotNull();

        assertThat(r.getBody().getDirected().stream().filter(x -> {
            return x.getMovie().getMovieId().equals(movieId);
        })).hasSize(0);
    }

    @Test
    public void testCantFindMovieWhenDeletingDirectedRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById("", "");

        DirectorController directorController = new DirectorController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            directorController.deleteDirected(personId, "");
        });

        assertThat(exception.getMessage()).isEqualTo("Movie not found!");
    }

    @Test
    public void testCantFindPersonWhenDeletingDirectedRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById("", "", "");
        mocks.findMovieById(movieId, title);

        DirectorController directorController = new DirectorController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            directorController.deleteDirected("", movieId);
        });

        assertThat(exception.getMessage()).isEqualTo("Person not found!");
    }
}
