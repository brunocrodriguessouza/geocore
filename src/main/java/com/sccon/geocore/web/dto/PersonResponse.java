package com.sccon.geocore.web.dto;

import java.time.LocalDate;

public record PersonResponse(
        Long id,
        String name,
        LocalDate birthDate,
        LocalDate admissionDate
) {}
