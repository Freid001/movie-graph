package com.movies.controller;

import com.movies.exception.NotFoundException;
import com.movies.model.dto.CreateActedIn;
import com.movies.model.dto.CreateUpdatePerson;
import com.movies.model.dto.CreateReviewed;
import com.movies.model.node.Movie;
import com.movies.model.node.Person;
import com.movies.model.relationship.*;
import com.movies.repositories.MovieRepository;
import com.movies.repositories.PersonRepository;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Person"})
public class PersonController {

	private final ModelMapper modelMapper;
	private final PersonRepository personRepository;
	private final MovieRepository movieRepository;

	@Autowired
	public PersonController(ModelMapper modelMapper, PersonRepository personRepository, MovieRepository movieRepository) {
		this.modelMapper = modelMapper;
		this.personRepository = personRepository;
		this.movieRepository = movieRepository;
	}

	@GetMapping("person/{personId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Person> getPerson(@PathVariable("personId") String personId) throws NotFoundException {
		Person person = this.personRepository.findById(personId).orElseThrow(() -> new NotFoundException("Person not found!"));

		return new ResponseEntity(person, HttpStatus.OK);
	}

	@PostMapping("person")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Person> postPerson(@RequestBody @Valid CreateUpdatePerson createUpdatePerson) {
		Person person = this.modelMapper.map(createUpdatePerson, Person.class);

		this.personRepository.save(person);

		return new ResponseEntity(person, HttpStatus.CREATED);
	}

	@PostMapping("person/{personId}/directed/{movieId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Person> directed(@PathVariable("personId") String personId,
										   @PathVariable("movieId") String movieId) throws NotFoundException {

		Person person = this.personRepository.findById(personId).orElseThrow(() -> new NotFoundException("Person not found!"));
		Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("Movie not found!"));

		person.directed(Directed.builder().movie(movie).build());

		this.personRepository.save(person);

		return new ResponseEntity(person, HttpStatus.OK);
	}

	@PostMapping("person/{personId}/wrote/{movieId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Person> wrote(@PathVariable("personId") String personId,
										@PathVariable("movieId") String movieId) throws NotFoundException {

		Person person = this.personRepository.findById(personId).orElseThrow(() -> new NotFoundException("Person not found!"));
		Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("Movie not found!"));

		person.wrote(Wrote.builder().movie(movie).build());

		this.personRepository.save(person);

		return new ResponseEntity(person, HttpStatus.OK);
	}

	@PostMapping("person/{personId}/produced/{movieId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Person> produced(@PathVariable("personId") String personId,
										   @PathVariable("movieId") String movieId) throws NotFoundException {

		Person person = this.personRepository.findById(personId).orElseThrow(() -> new NotFoundException("Person not found!"));
		Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("Movie not found!"));

		person.produced(Produced.builder().movie(movie).build());

		this.personRepository.save(person);

		return new ResponseEntity(person, HttpStatus.OK);
	}

	@PostMapping("person/{personId}/actedIn/{movieId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Person> actedIn(@PathVariable("personId") String personId,
										  @PathVariable("movieId") String movieId,
										  @RequestBody @Valid CreateActedIn actedIn) throws NotFoundException {

		Person person = this.personRepository.findById(personId).orElseThrow(() -> new NotFoundException("Person not found!"));
		Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("Movie not found!"));

		person.actedIn(ActedIn.builder().movie(movie).character(actedIn.character).build());

		this.personRepository.save(person);

		return new ResponseEntity(person, HttpStatus.OK);
	}

	@PostMapping("person/{personId}/reviewed/{movieId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Person> reviewed(@PathVariable("personId") String personId,
										   @PathVariable("movieId") String movieId,
										   @RequestBody @Valid CreateReviewed reviewed) throws NotFoundException {

		Person person = this.personRepository.findById(personId).orElseThrow(() -> new NotFoundException("Person not found!"));
		Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("Movie not found!"));

		person.reviewed(Reviewed.builder().movie(movie).rating(reviewed.rating).build());

		this.personRepository.save(person);

		return new ResponseEntity(person, HttpStatus.OK);
	}
}
