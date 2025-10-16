package com.sccon.geocore.web.dto;

/**
 * DTO que representa uma resposta de idade.
 * Contém a idade detalhada em anos, meses e dias.
 * 
 * @param years número de anos
 * @param months número de meses
 * @param days número de dias
 */
public record AgeResponse(
        int years,
        int months,
        int days
) {}
