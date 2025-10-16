package com.sccon.geocore.service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class AgeService {

    private final Clock clock;

    public AgeService(Clock clock) {
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    public record Age(int years, int months, int days) {}

    public Age diff(LocalDate birthDate) {
        Objects.requireNonNull(birthDate, "birthDate");
        validateBirthDateIsNotInFuture(birthDate);

        LocalDate today = LocalDate.now(clock);
        Period period = Period.between(birthDate, today);
        return new Age(period.getYears(), period.getMonths(), period.getDays());
    }

    public int yearsOfService(LocalDate admissionDate) {
        Objects.requireNonNull(admissionDate, "admissionDate");
        validateAdmissionDateIsNotInFuture(admissionDate);

        LocalDate today = LocalDate.now(clock);
        return Period.between(admissionDate, today).getYears();
    }

    private void validateBirthDateIsNotInFuture(LocalDate birthDate) {
        if (birthDate.isAfter(LocalDate.now(clock))) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }
    }

    private void validateAdmissionDateIsNotInFuture(LocalDate admissionDate) {
        if (admissionDate.isAfter(LocalDate.now(clock))) {
            throw new IllegalArgumentException("Admission date cannot be in the future");
        }
    }
}