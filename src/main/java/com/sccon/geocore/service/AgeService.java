package com.sccon.geocore.service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * Serviço responsável por cálculos relacionados à idade.
 * Fornece métodos para calcular idade e anos de serviço baseados em datas.
 */
public class AgeService {

    private final Clock clock;

    /**
     * Construtor do serviço de idade.
     * 
     * @param clock relógio para obter a data atual
     */
    public AgeService(Clock clock) {
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    /**
     * Record que representa uma idade com anos, meses e dias.
     * 
     * @param years número de anos
     * @param months número de meses
     * @param days número de dias
     */
    public record Age(int years, int months, int days) {}

    /**
     * Calcula a diferença entre a data de nascimento e a data atual.
     * 
     * @param birthDate data de nascimento
     * @return objeto Age contendo anos, meses e dias
     * @throws IllegalArgumentException se a data de nascimento for no futuro
     */
    public Age diff(LocalDate birthDate) {
        Objects.requireNonNull(birthDate, "birthDate");
        validateBirthDateIsNotInFuture(birthDate);

        LocalDate today = LocalDate.now(clock);
        Period period = Period.between(birthDate, today);
        return new Age(period.getYears(), period.getMonths(), period.getDays());
    }

    /**
     * Calcula os anos de serviço baseado na data de admissão.
     * 
     * @param admissionDate data de admissão
     * @return número de anos de serviço
     * @throws IllegalArgumentException se a data de admissão for no futuro
     */
    public int yearsOfService(LocalDate admissionDate) {
        Objects.requireNonNull(admissionDate, "admissionDate");
        validateAdmissionDateIsNotInFuture(admissionDate);

        LocalDate today = LocalDate.now(clock);
        return Period.between(admissionDate, today).getYears();
    }

    /**
     * Valida se a data de nascimento não está no futuro.
     * 
     * @param birthDate data de nascimento a ser validada
     * @throws IllegalArgumentException se a data for no futuro
     */
    private void validateBirthDateIsNotInFuture(LocalDate birthDate) {
        if (birthDate.isAfter(LocalDate.now(clock))) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }
    }

    /**
     * Valida se a data de admissão não está no futuro.
     * 
     * @param admissionDate data de admissão a ser validada
     * @throws IllegalArgumentException se a data for no futuro
     */
    private void validateAdmissionDateIsNotInFuture(LocalDate admissionDate) {
        if (admissionDate.isAfter(LocalDate.now(clock))) {
            throw new IllegalArgumentException("Admission date cannot be in the future");
        }
    }
}