package com.addressbook.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PersonRelationship {
    Long id;

    public enum TYPE {
        SPOUSE,
        SIBLING,
        FAMILY,
        CLOSE_FRIEND,
        FRIEND
    }

    TYPE type;
}
