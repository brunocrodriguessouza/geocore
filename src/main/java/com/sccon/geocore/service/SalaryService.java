package com.sccon.geocore.service;

import java.time.LocalDate;
import java.time.Period;

/**
 * Serviço responsável por cálculos de salário.
 * Calcula salários baseados no tempo de serviço com aumentos anuais.
 */
public class SalaryService {

    /** Salário base inicial */
    private static final double BASE_SALARY = 1558.00;
    /** Salário mínimo de referência */
    private static final double MINIMUM_WAGE = 1302.00;
    /** Percentual de aumento anual */
    private static final double ANNUAL_INCREASE_PERCENTAGE = 0.18;
    /** Aumento fixo anual */
    private static final double ANNUAL_FIXED_INCREASE = 500.00;

    /**
     * Calcula o salário baseado na data de admissão e tipo de saída.
     * 
     * @param admissionDate data de admissão
     * @param outputType tipo de saída (full, min)
     * @return salário calculado
     * @throws IllegalArgumentException se a data de admissão for nula ou tipo de saída inválido
     */
    public double calculateSalary(LocalDate admissionDate, String outputType) {
        validateAdmissionDate(admissionDate);
        
        double currentSalary = BASE_SALARY;
        int yearsWorked = calculateYearsWorked(admissionDate);
        
        currentSalary = applyAnnualIncreases(currentSalary, yearsWorked);
        
        return formatSalaryOutput(currentSalary, outputType);
    }

    /**
     * Valida se a data de admissão não é nula.
     * 
     * @param admissionDate data de admissão a ser validada
     * @throws IllegalArgumentException se a data for nula
     */
    private void validateAdmissionDate(LocalDate admissionDate) {
        if (admissionDate == null) {
            throw new IllegalArgumentException("Data de admissão não pode ser nula");
        }
    }

    /**
     * Calcula o número de anos trabalhados desde a admissão.
     * 
     * @param admissionDate data de admissão
     * @return número de anos trabalhados
     */
    private int calculateYearsWorked(LocalDate admissionDate) {
        return Period.between(admissionDate, LocalDate.now()).getYears();
    }

    /**
     * Aplica os aumentos anuais ao salário.
     * 
     * @param salary salário base
     * @param years número de anos para aplicar aumentos
     * @return salário com aumentos aplicados
     */
    private double applyAnnualIncreases(double salary, int years) {
        for (int year = 0; year < years; year++) {
            salary = applySingleYearIncrease(salary);
        }
        return salary;
    }

    /**
     * Aplica o aumento de um ano ao salário.
     * 
     * @param salary salário atual
     * @return salário com aumento aplicado
     */
    private double applySingleYearIncrease(double salary) {
        return salary * (1 + ANNUAL_INCREASE_PERCENTAGE) + ANNUAL_FIXED_INCREASE;
    }

    /**
     * Formata a saída do salário baseado no tipo solicitado.
     * 
     * @param salary salário calculado
     * @param outputType tipo de saída (full, min)
     * @return salário formatado
     * @throws IllegalArgumentException se o tipo de saída for inválido
     */
    private double formatSalaryOutput(double salary, String outputType) {
        return switch (outputType.toLowerCase()) {
            case "full" -> roundUpToTwoDecimals(salary);
            case "min" -> roundUpToTwoDecimals(salary / MINIMUM_WAGE);
            default -> throw new IllegalArgumentException("Tipo de saída inválido: " + outputType + 
                    ". Valores aceitos: full, min");
        };
    }

    /**
     * Arredonda um valor para cima com duas casas decimais.
     * 
     * @param value valor a ser arredondado
     * @return valor arredondado
     */
    private double roundUpToTwoDecimals(double value) {
        return Math.ceil(value * 100) / 100;
    }
}
