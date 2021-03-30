package com.addressbook.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import javax.validation.constraints.Email;

@AllArgsConstructor
@Getter
abstract public class Contact {
    @Id
    @GeneratedValue
    Long id;

    @JsonView(View.Base.class)
    String address;

    @JsonView(View.Base.class)
    @Email(message = "Email should be valid")
    String email;

    @JsonView(View.Base.class)
    String phoneNumber;
}
