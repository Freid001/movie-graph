package com.addressbook.controller;

import com.addressbook.exception.NotFoundException;
import com.addressbook.model.Person;
import com.addressbook.model.PersonRelationship;
import com.addressbook.repositories.PersonRepository;
import com.addressbook.model.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.log4j.Log4j2;
import javax.validation.*;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/api/v1")
@Api(tags = { "Person" })
public class PersonController {

	private final PersonRepository personRepository;

	@Autowired
	public PersonController(PersonRepository personRepository){
		this.personRepository = personRepository;
	}

	@GetMapping("person/{personId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Person> getPerson(@PathVariable("personId") long id) throws NotFoundException {
		Person person = this.personRepository.findByPersonId(id); //.orElseThrow(() -> new NotFoundException("Person not found!"));

		return new ResponseEntity(person, HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("person/{personId}/relationship")
	public ResponseEntity<Person> postRelationships(@PathVariable("personId") long id, @RequestBody @Valid ArrayList<PersonRelationship> relationship) {
		Person person = this.personRepository.findByPersonId(id); //.orElseThrow(() -> new NotFoundException("Person not found!"));

		// Retrieves all person ids.
		List<Long> personIds = relationship
				.stream()
				.map(PersonRelationship::getId)
				.collect(Collectors.toList());

		// Retrieves all person's who we have a relationship with.
		Map<Long, Person> relationshipWithPersons = this.personRepository.findAllById(personIds)
				.stream()
				.collect(Collectors.toMap(Person::getPersonId, x -> x));

		// Append relationship
		for (PersonRelationship pr : relationship) {
			switch (pr.getType()) {
				case SPOUSE:
					person.appendSpouse(relationshipWithPersons.get(pr.getId()));
					break;

				case SIBLING:
					person.appendSibling(relationshipWithPersons.get(pr.getId()));
					break;

				case FAMILY:
					person.appendFamily(relationshipWithPersons.get(pr.getId()));
					break;

				case CLOSE_FRIEND:
					person.appendCloseFriend(relationshipWithPersons.get(pr.getId()));
					break;

				case FRIEND:
					person.appendFriend(relationshipWithPersons.get(pr.getId()));
					break;
			}
		}

		// Save person
		this.personRepository.save(person);

		return new ResponseEntity(person, HttpStatus.OK);
	}

	@PostMapping("person")
	@ResponseStatus(HttpStatus.CREATED)
	@JsonView(View.Base.class)
	public ResponseEntity<Person> postPerson(@RequestBody @Valid Person person) {
		this.personRepository.save(person);

		return new ResponseEntity(person, HttpStatus.CREATED);
	}
}

// todo:
// restrict input fields? @JsonView(View.Base.class) ?
// don't use ID?
// indexing? - https://docs.spring.io/spring-data/neo4j/docs/current/reference/html/#reference:indexing:creation
//
// unit tests
// cucumber
