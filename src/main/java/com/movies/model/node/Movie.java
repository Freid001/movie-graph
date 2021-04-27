package com.movies.model.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Data
@Node
public class Movie {
    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    String movieId;

    String title;
}
