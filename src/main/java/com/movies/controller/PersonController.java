package com.movies.controller;

import com.movies.exception.NotFoundException;
import com.movies.model.dto.CreateUpdatePerson;
import com.movies.model.node.Person;
import com.movies.repositories.PersonRepository;
import com.movies.setup.SetupBeans;
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
@Api(tags = { SetupBeans.PERSON })
public class PersonController {

	private final ModelMapper modelMapper;
	private final PersonRepository personRepository;

	@Autowired
	public PersonController(ModelMapper modelMapper, PersonRepository personRepository) {
		this.modelMapper = modelMapper;
		this.personRepository = personRepository;
	}

	@Timed(
			value="person.get",
			histogram = true,
			percentiles = {0.95,0.99}
	)
	@GetMapping("person/{personId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Person> getPerson(@PathVariable("personId") String personId) throws NotFoundException {
		Person person = this.personRepository.findById(personId).orElseThrow(() -> new NotFoundException("Person not found!"));

		return new ResponseEntity(person, HttpStatus.OK);
	}

	@Timed(
			value="person.create",
			histogram = true,
			percentiles = {0.95,0.99}
	)
	@PostMapping("person")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Person> postPerson(@RequestBody @Valid CreateUpdatePerson createUpdatePerson) {
		Person person = this.personRepository.save(this.modelMapper.map(createUpdatePerson, Person.class));

		return new ResponseEntity(person, HttpStatus.CREATED);
	}
}
