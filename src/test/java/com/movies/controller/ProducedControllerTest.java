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
import static org.mockito.Mockito.*;

public class ProducedControllerTest {
    String personId = "55df8a92-0964-4ea3-8627-4850f85793d3";
    String firstName = "Robert";
    String lastName = "Watts";
    String movieId = "5f524a67-e316-41d2-b555-e432eae78afe";
    String title = "Indiana Jones and the Last Crusade";

    @Test
    public void testCanCreateProducedRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById(movieId, title);
        mocks.savePerson(personId, firstName, lastName);

        ProducerController producerController = new ProducerController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        ResponseEntity<Person> r = producerController.postProduced(personId, movieId);

        assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(r.getBody()).isNotNull();

        assertThat(r.getBody().getProduced().stream().filter(x -> {
            return x.getMovie().getMovieId().equals(movieId);
        })).hasSize(1);
    }

    @Test
    public void testCantFindMovieWhenCreatingProducedRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById("", "");

        ProducerController producerController = new ProducerController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            producerController.postProduced(personId, "");
        });

        assertThat(exception.getMessage()).isEqualTo("Movie not found!");
    }

    @Test
    public void testCantFindPersonWhenCreatingProducedRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById("", "", "");
        mocks.findMovieById(movieId, title);

        ProducerController producerController = new ProducerController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            producerController.postProduced("", movieId);
        });

        assertThat(exception.getMessage()).isEqualTo("Person not found!");
    }

    @Test
    public void testCanDeleteProducedRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById(movieId, title);
        mocks.savePerson(personId, firstName, lastName);

        ProducerController producerController = new ProducerController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        ResponseEntity<Person> r = producerController.deleteProduced(personId, movieId);

        assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(r.getBody()).isNotNull();

        assertThat(r.getBody().getProduced().stream().filter(x -> {
            return x.getMovie().getMovieId().equals(movieId);
        })).hasSize(0);
    }

    @Test
    public void testCantFindMovieWhenDeletingProducedRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.findMovieById("", "");

        ProducerController producerController = new ProducerController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            producerController.deleteProduced(personId, "");
        });

        assertThat(exception.getMessage()).isEqualTo("Movie not found!");
    }

    @Test
    public void testCantFindPersonWhenDeletingProducedRelationship() {
        Mocks mocks = new Mocks();
        mocks.findPersonById("", "", "");
        mocks.findMovieById(movieId, title);

        ProducerController producerController = new ProducerController(
                mocks.getPersonRepository(),
                mocks.getMovieRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            producerController.deleteProduced("", movieId);
        });

        assertThat(exception.getMessage()).isEqualTo("Person not found!");
    }
}
