package com.addressbook.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import org.neo4j.ogm.annotation.Index;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;

@Node
@Getter
public class Company extends Contact {
    @Index(unique = true)
    @JsonView(View.Base.class)
    UUID companyId;

    @Builder
    public Company(Long id, UUID companyId, String name, String address, String email, String phoneNumber) {
        super(id, address, email, phoneNumber);

        this.companyId = companyId;
        this.name = name;
    }

    @JsonView(View.Base.class)
    @NotEmpty(message = "Please provide a name")
    String name;

    @Relationship(type = "FULL_TIME_EMPLOYEE")
    public Set<Person> fullTimeEmployee;

    @Relationship(type = "PART_TIME_EMPLOYEE")
    public Set<Person> partTimeEmployee;

    @Relationship(type = "CONTRACTOR")
    public Set<Person> contractor;

    @Relationship(type = "VOLUNTEER")
    public Set<Person> volunteer;
}
