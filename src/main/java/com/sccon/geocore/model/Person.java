package com.sccon.geocore.model;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public record Person(
        Long id,
        String name,
        LocalDate birthDate,
        LocalDate admissionDate
) {
    public Person {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(birthDate, "birthDate");
        Objects.requireNonNull(admissionDate, "admissionDate");
        if (admissionDate.isBefore(birthDate)) {
            throw new IllegalArgumentException("admissionDate cannot be before birthDate");
        }
    }

    public Person withName(String newName) {
        return new Person(id, newName, birthDate, admissionDate);
    }

    public Person withBirthDate(LocalDate newBirthDate) {
        return new Person(id, name, newBirthDate, admissionDate);
    }

    public Person withAdmissionDate(LocalDate newAdmissionDate) {
        return new Person(id, name, birthDate, newAdmissionDate);
    }

    public int yearsOfService(Clock clock) {
        return Period.between(admissionDate, LocalDate.now(clock)).getYears();
    }
}
