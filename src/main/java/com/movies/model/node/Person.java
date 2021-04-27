package com.movies.model.node;

import com.movies.model.relationship.*;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.HashSet;
import java.util.Set;

@Data
@Node
public class Person {
    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    String personId;

    String firstName;

    String lastName;

    @Relationship(type = "DIRECTED", direction = Relationship.Direction.OUTGOING)
    private Set<Directed> directed;

    @Relationship(type = "WROTE", direction = Relationship.Direction.OUTGOING)
    private Set<Wrote> wrote;

    @Relationship(type = "PRODUCED", direction = Relationship.Direction.OUTGOING)
    private Set<Produced> produced;

    @Relationship(type = "ACTED_IN", direction = Relationship.Direction.OUTGOING)
    private Set<ActedIn> actedIn;

    @Relationship(type = "REVIEWED", direction = Relationship.Direction.OUTGOING)
    private Set<Reviewed> reviewed;

    public void directed(Directed directed) {
        if (this.directed == null) {
            this.directed = new HashSet<>();
        }

        this.directed.add(directed);
    }

    public void wrote(Wrote wrote) {
        if (this.wrote == null) {
            this.wrote = new HashSet<>();
        }

        this.wrote.add(wrote);
    }

    public void produced(Produced produced) {
        if (this.produced == null) {
            this.produced = new HashSet<>();
        }

        this.produced.add(produced);
    }

    public void actedIn(ActedIn actedIn) {
        if (this.actedIn == null) {
            this.actedIn = new HashSet<>();
        }

        this.actedIn.add(actedIn);
    }

    public void reviewed(Reviewed reviewed) {
        if (this.reviewed == null) {
            this.reviewed = new HashSet<>();
        }

        this.reviewed.add(reviewed);
    }
}
