package com.sccon.geocore.web.mapper;

import com.sccon.geocore.model.Person;
import com.sccon.geocore.web.dto.PersonResponse;

public final class PersonMapper {

    private PersonMapper() {
    }

    public static PersonResponse toResponse(Person person) {
        if (person == null) {
            return null;
        }
        return new PersonResponse(
                person.id(),
                person.name(),
                person.birthDate(),
                person.admissionDate()
        );
    }
}
