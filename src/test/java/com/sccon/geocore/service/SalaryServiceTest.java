package com.sccon.geocore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SalaryServiceTest {

    private SalaryService salaryService;

    @BeforeEach
    void setUp() {
        salaryService = new SalaryService();
    }

    @Test
    void testCalculateSalary_NewEmployee_ShouldReturnBaseSalary() {
        // Arrange
        LocalDate admissionDate = LocalDate.now().minusDays(1);
        
        // Act
        double salary = salaryService.calculateSalary(admissionDate, "full");
        
        // Assert
        assertEquals(1558.00, salary, 0.01);
    }

    @Test
    void testCalculateSalary_OneYearOfService_ShouldApplyCorrectIncrease() {
        // Arrange
        LocalDate admissionDate = LocalDate.now().minusYears(1);
        
        // Act
        double salary = salaryService.calculateSalary(admissionDate, "full");
        
        // Assert
        double expectedSalary = (1558.00 * 1.18 + 500.00);
        assertEquals(Math.ceil(expectedSalary * 100) / 100, salary, 0.01);
    }

    @Test
    void testCalculateSalary_TwoYearsOfService_ShouldApplyCorrectIncreases() {
        // Arrange
        LocalDate admissionDate = LocalDate.now().minusYears(2);
        
        // Act
        double salary = salaryService.calculateSalary(admissionDate, "full");
        
        // Assert
        double year1Salary = 1558.00 * 1.18 + 500.00;
        double year2Salary = year1Salary * 1.18 + 500.00;
        double expectedSalary = Math.ceil(year2Salary * 100) / 100;
        assertEquals(expectedSalary, salary, 0.01);
    }

    @Test
    void testCalculateSalary_OutputMin_ShouldReturnSalaryInMinimumWages() {
        // Arrange
        LocalDate admissionDate = LocalDate.now().minusYears(1);
        
        // Act
        double salaryInMin = salaryService.calculateSalary(admissionDate, "min");
        
        // Assert
        double expectedSalaryInMin = Math.ceil((2338.44 / 1302.00) * 100) / 100;
        assertEquals(expectedSalaryInMin, salaryInMin, 0.01);
    }

    @Test
    void testCalculateSalary_InvalidOutput_ShouldThrowException() {
        // Arrange
        LocalDate admissionDate = LocalDate.now().minusYears(1);
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> salaryService.calculateSalary(admissionDate, "invalid")
        );
        
        assertTrue(exception.getMessage().contains("Tipo de saída inválido"));
    }

    @Test
    void testCalculateSalary_NullAdmissionDate_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> salaryService.calculateSalary(null, "full")
        );
        
        assertEquals("Data de admissão não pode ser nula", exception.getMessage());
    }

    @Test
    void testCalculateSalary_ExampleFromSpecification() {
        // Arrange
        LocalDate admissionDate = LocalDate.of(2020, 5, 10);
        
        // Act
        double salary = salaryService.calculateSalary(admissionDate, "full");
        
        // Assert
        assertTrue(salary > 1558.0, 
            "Salário deve ser maior que o inicial (R$ 1558). Valor atual: " + salary);

        assertTrue(salary >= 3000.0, 
            "Salário deve ser pelo menos R$ 3000 para 4+ anos de trabalho. Valor atual: " + salary);
        assertTrue(salary <= 15000.0, 
            "Salário deve ser no máximo R$ 15000 para 4+ anos de trabalho. Valor atual: " + salary);

        assertTrue(salary > 0, 
            "Salário deve ser positivo. Valor atual: " + salary);
    }

    @Test
    void testCalculateSalary_CurrentDateExample() {
        // Arrange
        LocalDate admissionDate = LocalDate.of(2020, 5, 10);
        
        // Act
        double salary = salaryService.calculateSalary(admissionDate, "full");
        
        // Assert
        assertTrue(salary > 1558.0, 
            "Salário deve ser maior que o inicial (R$ 1558). Valor atual: " + salary);

        assertTrue(salary >= 2000.0, 
            "Salário deve ser pelo menos R$ 2000. Valor atual: " + salary);
        assertTrue(salary <= 20000.0, 
            "Salário deve ser no máximo R$ 20000. Valor atual: " + salary);

        assertTrue(salary > 0, 
            "Salário deve ser positivo. Valor atual: " + salary);
    }

    @Test
    void testCalculateSalary_RealWorldExample() {
        // Arrange
        LocalDate admissionDate = LocalDate.of(2020, 5, 10);
        
        // Act
        double salary = salaryService.calculateSalary(admissionDate, "full");
        
        // Assert
        assertTrue(salary > 1558.0, 
            "Salário deve ser maior que o inicial (R$ 1558). Valor atual: " + salary);

        assertTrue(salary >= 4000.0, 
            "Salário deve ser pelo menos R$ 4000 para 4+ anos. Valor atual: " + salary);
        assertTrue(salary <= 10000.0, 
            "Salário deve ser no máximo R$ 10000 para 4+ anos. Valor atual: " + salary);
    }

    @Test
    void testCalculateSalary_ExactCalculationForTwoYears() {
        // Arrange
        LocalDate admissionDate = LocalDate.now().minusYears(2).minusDays(365);
        
        // Act
        double salary = salaryService.calculateSalary(admissionDate, "full");
        
        // Assert
        assertTrue(salary > 2338.0, 
            "Salário deve ser maior que 1 ano (R$ 2338). Valor atual: " + salary);
        assertTrue(salary < 4350.0, 
            "Salário deve ser menor que 3 anos (R$ 4350). Valor atual: " + salary);

        assertTrue(salary >= 3000.0, 
            "Salário deve ser pelo menos R$ 3000 para ~2 anos. Valor atual: " + salary);
        assertTrue(salary <= 4500.0, 
            "Salário deve ser no máximo R$ 4500 para ~2 anos. Valor atual: " + salary);
    }

    @Test
    void testCalculateSalary_ExactCalculationForThreeYears() {
        // Arrange
        LocalDate admissionDate = LocalDate.now().minusYears(3).minusDays(365);
        
        // Act
        double salary = salaryService.calculateSalary(admissionDate, "full");
        
        // Assert
        assertTrue(salary > 3259.0, 
            "Salário deve ser maior que 2 anos (R$ 3259). Valor atual: " + salary);
        assertTrue(salary < 5630.0, 
            "Salário deve ser menor que 4 anos (R$ 5630). Valor atual: " + salary);

        assertTrue(salary >= 4000.0, 
            "Salário deve ser pelo menos R$ 4000 para ~3 anos. Valor atual: " + salary);
        assertTrue(salary <= 6000.0, 
            "Salário deve ser no máximo R$ 6000 para ~3 anos. Valor atual: " + salary);
    }
}
