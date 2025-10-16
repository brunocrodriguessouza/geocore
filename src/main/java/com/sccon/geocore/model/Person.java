package com.sccon.geocore.model;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * Representa uma pessoa no sistema.
 * Esta classe é um record que contém informações básicas de uma pessoa,
 * incluindo dados pessoais e de admissão.
 * 
 * @param id identificador único da pessoa
 * @param name nome da pessoa
 * @param birthDate data de nascimento
 * @param admissionDate data de admissão na empresa
 */
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

    /**
     * Cria uma nova instância de Person com o nome alterado.
     * 
     * @param newName novo nome
     * @return nova instância com o nome alterado
     */
    public Person withName(String newName) {
        return new Person(id, newName, birthDate, admissionDate);
    }

    /**
     * Cria uma nova instância de Person com a data de nascimento alterada.
     * 
     * @param newBirthDate nova data de nascimento
     * @return nova instância com a data de nascimento alterada
     */
    public Person withBirthDate(LocalDate newBirthDate) {
        return new Person(id, name, newBirthDate, admissionDate);
    }

    /**
     * Cria uma nova instância de Person com a data de admissão alterada.
     * 
     * @param newAdmissionDate nova data de admissão
     * @return nova instância com a data de admissão alterada
     */
    public Person withAdmissionDate(LocalDate newAdmissionDate) {
        return new Person(id, name, birthDate, newAdmissionDate);
    }

    /**
     * Calcula os anos de serviço da pessoa baseado na data de admissão.
     * 
     * @param clock relógio para obter a data atual
     * @return número de anos de serviço
     */
    public int yearsOfService(Clock clock) {
        return Period.between(admissionDate, LocalDate.now(clock)).getYears();
    }
}
