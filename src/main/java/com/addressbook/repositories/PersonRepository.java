package com.addressbook.repositories;

import com.addressbook.model.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PersonRepository extends Neo4jRepository<Person, Long> {
    Person findByPersonId(Long personId);
}