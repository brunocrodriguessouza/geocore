package com.sccon.geocore.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.Optional;

/**
 * DTO para atualização parcial de uma pessoa.
 * Todos os campos são opcionais, permitindo atualizações parciais.
 * 
 * @param name novo nome (opcional)
 * @param birthDate nova data de nascimento (opcional, deve ser no passado)
 * @param admissionDate nova data de admissão (opcional, deve ser no passado ou presente)
 */
public record UpdatePersonRequest(
        Optional<@NotBlank(message = "Nome não pode ser vazio") String> name,
        Optional<LocalDate> birthDate,
        Optional<@PastOrPresent(message = "Data de admissão deve ser no passado ou presente") LocalDate> admissionDate
) {}
