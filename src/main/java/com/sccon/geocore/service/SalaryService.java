package com.sccon.geocore.service;

import java.time.LocalDate;
import java.time.Period;

public class SalaryService {

    private static final double BASE_SALARY = 1558.00;
    private static final double MINIMUM_WAGE = 1302.00;
    private static final double ANNUAL_INCREASE_PERCENTAGE = 0.18;
    private static final double ANNUAL_FIXED_INCREASE = 500.00;

    public double calculateSalary(LocalDate admissionDate, String outputType) {
        validateAdmissionDate(admissionDate);
        
        double currentSalary = BASE_SALARY;
        int yearsWorked = calculateYearsWorked(admissionDate);
        
        currentSalary = applyAnnualIncreases(currentSalary, yearsWorked);
        
        return formatSalaryOutput(currentSalary, outputType);
    }

    private void validateAdmissionDate(LocalDate admissionDate) {
        if (admissionDate == null) {
            throw new IllegalArgumentException("Data de admissão não pode ser nula");
        }
    }

    private int calculateYearsWorked(LocalDate admissionDate) {
        return Period.between(admissionDate, LocalDate.now()).getYears();
    }

    private double applyAnnualIncreases(double salary, int years) {
        for (int year = 0; year < years; year++) {
            salary = applySingleYearIncrease(salary);
        }
        return salary;
    }

    private double applySingleYearIncrease(double salary) {
        return salary * (1 + ANNUAL_INCREASE_PERCENTAGE) + ANNUAL_FIXED_INCREASE;
    }

    private double formatSalaryOutput(double salary, String outputType) {
        return switch (outputType.toLowerCase()) {
            case "full" -> roundUpToTwoDecimals(salary);
            case "min" -> roundUpToTwoDecimals(salary / MINIMUM_WAGE);
            default -> throw new IllegalArgumentException("Tipo de saída inválido: " + outputType + 
                    ". Valores aceitos: full, min");
        };
    }

    private double roundUpToTwoDecimals(double value) {
        return Math.ceil(value * 100) / 100;
    }
}
