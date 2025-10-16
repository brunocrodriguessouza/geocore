package com.sccon.geocore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class AgeServiceTest {

    private AgeService ageService;
    private Clock fixedClock;

    @BeforeEach
    void setUp() {
        fixedClock = Clock.fixed(
            LocalDate.of(2023, 2, 1).atStartOfDay(ZoneId.systemDefault()).toInstant(),
            ZoneId.systemDefault()
        );
        ageService = new AgeService(fixedClock);
    }

    @Test
    void testDiff_ValidBirthDate_ShouldReturnCorrectAge() {
        // Arrange
        LocalDate birthDate = LocalDate.of(2000, 4, 6);
        
        // Act
        AgeService.Age age = ageService.diff(birthDate);
        
        // Assert
        // De 06/04/2000 a 01/02/2023 = 22 anos, 9 meses, 26 dias
        assertEquals(22, age.years());
        assertEquals(9, age.months());
        assertEquals(26, age.days());
    }

    @Test
    void testDiff_ExampleFromSpecification_ShouldReturnCorrectValues() {
        // Arrange - José da Silva, nascido em 06/04/2000
        LocalDate birthDate = LocalDate.of(2000, 4, 6);
        
        // Act
        AgeService.Age age = ageService.diff(birthDate);
        
        // Assert - Conforme especificação do documento
        assertEquals(22, age.years());
        assertEquals(9, age.months());
        assertEquals(26, age.days());
    }

    @Test
    void testDiff_NullBirthDate_ShouldThrowException() {
        // Act & Assert
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> ageService.diff(null)
        );
        
        assertEquals("birthDate", exception.getMessage());
    }

    @Test
    void testDiff_FutureBirthDate_ShouldThrowException() {
        // Arrange
        LocalDate futureDate = LocalDate.now(fixedClock).plusDays(1);
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> ageService.diff(futureDate)
        );
        
        assertTrue(exception.getMessage().contains("Data de nascimento não pode ser no futuro"));
    }

    @Test
    void testYearsOfService_ValidAdmissionDate_ShouldReturnCorrectYears() {
        // Arrange
        LocalDate admissionDate = LocalDate.of(2020, 5, 10);
        
        // Act
        int yearsOfService = ageService.yearsOfService(admissionDate);
        
        // Assert
        // De 10/05/2020 a 01/02/2023 = 2 anos
        assertEquals(2, yearsOfService);
    }

    @Test
    void testYearsOfService_NullAdmissionDate_ShouldThrowException() {
        // Act & Assert
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> ageService.yearsOfService(null)
        );
        
        assertEquals("admissionDate", exception.getMessage());
    }

    @Test
    void testYearsOfService_FutureAdmissionDate_ShouldThrowException() {
        // Arrange
        LocalDate futureDate = LocalDate.now(fixedClock).plusDays(1);
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> ageService.yearsOfService(futureDate)
        );
        
        assertTrue(exception.getMessage().contains("Data de admissão não pode ser no futuro"));
    }
}
