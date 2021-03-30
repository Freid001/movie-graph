package com.addressbook.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.Index;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Node
@Getter
@Setter
public class Person extends Contact {
    @Index(unique = true)
    @JsonView(View.Base.class)
    UUID personId;

    @Builder
    public Person(Long id, UUID personId, String firstName, String lastName, Integer age, String address, String email, String phoneNumber) {
        super(id, address, email, phoneNumber);

        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @JsonView(View.Base.class)
    @NotEmpty(message = "Please provide a first name")
    String firstName;

    @JsonView(View.Base.class)
    @NotEmpty(message = "Please provide a last name")
    String lastName;

    @JsonView(View.Base.class)
    @Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 150, message = "Age should not be greater than 150")
    Integer age;

    @Relationship(type = "SPOUSE") //direction?
    public Set<Person> spouse;

    @Relationship(type = "SIBLING")
    public Set<Person> sibling;

    @Relationship(type = "FAMILY")
    public Set<Person> family;

    @Relationship(type = "CLOSE_FRIEND")
    public Set<Person> closeFriend;

    @Relationship(type = "FRIEND")
    public Set<Person> friend;

    public void appendSpouse(Person spouse) {
        if (this.spouse == null) {
            this.spouse = new HashSet<>();
        }

        this.spouse.add(spouse);
    }

    public void appendSibling(Person sibling) {
        if (this.sibling == null) {
            this.sibling = new HashSet<>();
        }

        this.sibling.add(sibling);
    }

    public void appendFamily(Person family) {
        if (this.family == null) {
            this.family = new HashSet<>();
        }

        this.family.add(family);
    }

    public void appendCloseFriend(Person closeFriend) {
        if (this.closeFriend == null) {
            this.closeFriend = new HashSet<>();
        }

        this.closeFriend.add(closeFriend);
    }

    public void appendFriend(Person friend) {
        if (this.friend == null) {
            this.friend = new HashSet<>();
        }

        this.friend.add(friend);
    }
}
