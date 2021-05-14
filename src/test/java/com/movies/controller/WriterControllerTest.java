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

public class WriterControllerTest {
    String personId = "01d272e3-de8f-4315-b390-b18f7be56e9e";
    String firstName = "George";
    String lastName = "Lucas";
    String movieId = "9e1e7a4d-bba1-4e16-adcc-0a8b68a85c7f";
    String title = "Indiana Jones and the Temple of Doom";

    @Test
    public void testCanCreateWroteRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById(movieId, title);
        mocks.savePerson(personId, firstName, lastName);

        WriterController writerController = new WriterController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        ResponseEntity<Person> r = writerController.postWrote(personId, movieId);

        assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(r.getBody()).isNotNull();

        assertThat(r.getBody().getWrote().stream().filter(x -> {
            return x.getMovie().getMovieId().equals(movieId);
        })).hasSize(1);
    }

    @Test
    public void testCantFindMovieWhenCreatingWroteRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById("", "");

        WriterController writerControlleer = new WriterController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            writerControlleer.postWrote(personId, "");
        });

        assertThat(exception.getMessage()).isEqualTo("Movie not found!");
    }

    @Test
    public void testCantFindPersonWhenCreatingWroteRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById("", "", "");
        mocks.findMovieById(movieId, title);

        WriterController writerController = new WriterController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            writerController.postWrote("", movieId);
        });

        assertThat(exception.getMessage()).isEqualTo("Person not found!");
    }


    @Test
    public void testCanDeleteWroteRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById(movieId, title);
        mocks.savePerson(personId, firstName, lastName);

        WriterController writerController = new WriterController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        ResponseEntity<Person> r = writerController.deleteWrote(personId, movieId);

        assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(r.getBody()).isNotNull();

        assertThat(r.getBody().getWrote().stream().filter(x -> {
            return x.getMovie().getMovieId().equals(movieId);
        })).hasSize(0);
    }

    @Test
    public void testCantFindMovieWhenDeletingWroteRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById("", "");

        WriterController writerController = new WriterController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            writerController.deleteWrote(personId, "");
        });

        assertThat(exception.getMessage()).isEqualTo("Movie not found!");
    }

    @Test
    public void testCantFindPersonWhenDeletingWroteRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById("", "", "");
        mocks.findMovieById(movieId, title);

        WriterController writerController = new WriterController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            writerController.deleteWrote("", movieId);
        });

        assertThat(exception.getMessage()).isEqualTo("Person not found!");
    }
}
