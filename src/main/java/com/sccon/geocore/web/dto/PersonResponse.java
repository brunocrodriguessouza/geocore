package com.sccon.geocore.web.dto;

import java.time.LocalDate;

/**
 * DTO que representa uma resposta de pessoa.
 * Contém os dados de uma pessoa para retorno em APIs.
 * 
 * @param id identificador único da pessoa
 * @param name nome da pessoa
 * @param birthDate data de nascimento
 * @param admissionDate data de admissão
 */
public record PersonResponse(
        Long id,
        String name,
        LocalDate birthDate,
        LocalDate admissionDate
) {}
