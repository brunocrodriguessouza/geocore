package com.sccon.geocore.web.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * DTO para criação de uma nova pessoa.
 * Contém os dados necessários para criar uma pessoa no sistema.
 * 
 * @param name nome da pessoa (obrigatório)
 * @param birthDate data de nascimento (obrigatória, deve ser no passado)
 * @param admissionDate data de admissão (obrigatória, deve ser no passado ou presente)
 */
public record CreatePersonRequest(
        @NotBlank(message = "Nome é obrigatório") String name,
        @NotNull(message = "Data de nascimento é obrigatória") LocalDate birthDate,
        @NotNull(message = "Data de admissão é obrigatória") @PastOrPresent(message = "Data de admissão deve ser no passado ou presente") LocalDate admissionDate
) {}