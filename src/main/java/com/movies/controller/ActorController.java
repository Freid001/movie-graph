package com.movies.controller;

import com.movies.exception.NotFoundException;
import com.movies.model.dto.CreateActedIn;
import com.movies.model.node.Movie;
import com.movies.model.node.Person;
import com.movies.model.relationship.ActedIn;
import com.movies.repositories.MovieRepository;
import com.movies.repositories.PersonRepository;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Person"})
public class ActorController {

    private final PersonRepository personRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public ActorController(PersonRepository personRepository, MovieRepository movieRepository) {
        this.personRepository = personRepository;
        this.movieRepository = movieRepository;
    }

    @PostMapping("person/{personId}/actedIn/{movieId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Person> postActedIn(@PathVariable("personId") String personId,
                                              @PathVariable("movieId") String movieId,
                                              @RequestBody @Valid CreateActedIn actedIn) throws NotFoundException {

        Person person = this.personRepository.findById(personId).orElseThrow(() -> new NotFoundException("Person not found!"));
        Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("Movie not found!"));

        person.appendActedIn(ActedIn.builder().movie(movie).character(actedIn.character).build());

        this.personRepository.save(person);

        return new ResponseEntity(person, HttpStatus.OK);
    }

    @DeleteMapping("person/{personId}/actedIn/{movieId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Person> deleteActedIn(@PathVariable("personId") String personId,
                                                @PathVariable("movieId") String movieId) throws NotFoundException {

        Person person = this.personRepository.findById(personId).orElseThrow(() -> new NotFoundException("Person not found!"));
        Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("Movie not found!"));

        person.removeActedIn(movie);

        this.personRepository.save(person);

        return new ResponseEntity(person, HttpStatus.OK);
    }
}
