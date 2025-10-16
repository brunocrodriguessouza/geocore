package com.sccon.geocore.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.Optional;

public record UpdatePersonRequest(
        Optional<@NotBlank(message = "Nome não pode ser vazio") String> name,
        Optional<@Past(message = "Data de nascimento deve ser no passado") LocalDate> birthDate,
        Optional<@PastOrPresent(message = "Data de admissão deve ser no passado ou presente") LocalDate> admissionDate
) {}
