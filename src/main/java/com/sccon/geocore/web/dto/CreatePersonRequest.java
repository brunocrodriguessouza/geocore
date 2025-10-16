package com.sccon.geocore.web.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreatePersonRequest(
        @NotBlank(message = "Nome é obrigatório") String name,
        @NotNull(message = "Data de nascimento é obrigatória") @Past(message = "Data de nascimento deve ser no passado") LocalDate birthDate,
        @NotNull(message = "Data de admissão é obrigatória") @PastOrPresent(message = "Data de admissão deve ser no passado ou presente") LocalDate admissionDate
) {}