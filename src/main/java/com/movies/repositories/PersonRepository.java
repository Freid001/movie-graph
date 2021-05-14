package com.movies.repositories;

import com.movies.model.node.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PersonRepository extends Neo4jRepository<Person, String> {}