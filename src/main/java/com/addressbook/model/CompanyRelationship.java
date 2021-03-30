package com.addressbook.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CompanyRelationship {

    public Long id;

    enum TYPE {
        FULL_TIME_EMPLOYEE,
        PART_TIME_EMPLOYEE,
        CONTRACTOR,
        VOLUNTEER
    }

    public TYPE type;
}
