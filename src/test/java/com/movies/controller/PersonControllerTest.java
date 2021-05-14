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

public class PersonControllerTest {
    String personId = "c41a8e31-4e87-4a8a-b195-79689403ad96";
    String firstName = "George";
    String lastName = "Lucas";

    @Test
    public void testCanRetrievePerson() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);

        PersonController personController = new PersonController(
                mocks.getModelMapper(),
                mocks.getPersonRepository()
        );

        ResponseEntity<Person> r = personController.getPerson(anyString());

        assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(r.getBody()).isNotNull();
        assertThat(r.getBody().getPersonId()).isEqualTo(personId);
        assertThat(r.getBody().getFirstName()).isEqualTo(firstName);
        assertThat(r.getBody().getLastName()).isEqualTo(lastName);
    }

    @Test
    public void testCantFindPerson() {
        Mocks mocks = new Mocks();
        mocks.findPersonById("", "", "");

        PersonController personController = new PersonController(
                mocks.getModelMapper(),
                mocks.getPersonRepository()
        );

        Exception exception = assertThrows(NotFoundException.class, () -> {
            personController.getPerson(anyString());
        });

        assertThat(exception.getMessage()).isEqualTo("Person not found!");
    }

    @Test
    public void testCanCreatePerson() {
        Mocks mocks = new Mocks();
        mocks.findPersonById(personId, firstName, lastName);
        mocks.savePerson(personId, firstName, lastName);

        PersonController personController = new PersonController(
                mocks.getModelMapper(),
                mocks.getPersonRepository()
        );

        ResponseEntity<Person> r = personController.postPerson(
                CreateUpdatePerson.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .build()
        );

        assertThat(r.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(r.getBody()).isNotNull();
        assertThat(r.getBody().getPersonId()).isEqualTo(personId);
        assertThat(r.getBody().getFirstName()).isEqualTo(firstName);
        assertThat(r.getBody().getLastName()).isEqualTo(lastName);
    }
}
