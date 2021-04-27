package com.movies.repositories;

import com.movies.model.node.Movie;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface MovieRepository extends Neo4jRepository<Movie, String> {}
