package com.movies.model.relationship;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movies.model.node.Movie;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Value
@Builder
@RelationshipProperties
public class Reviewed {
    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @TargetNode
    Movie movie;

    public Integer rating;
}
