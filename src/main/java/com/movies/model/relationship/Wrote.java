package com.movies.model.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movies.model.node.Movie;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Value
@Builder
@RelationshipProperties
public class Wrote {
    @Id
    @JsonIgnore
    Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @TargetNode
    Movie movie;
}
