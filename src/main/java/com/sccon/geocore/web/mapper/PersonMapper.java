package com.sccon.geocore.web.mapper;

import com.sccon.geocore.model.Person;
import com.sccon.geocore.web.dto.PersonResponse;

/**
 * Classe utilitária para mapeamento entre entidades Person e DTOs.
 * Fornece métodos estáticos para conversão de objetos.
 */
public final class PersonMapper {

    /**
     * Construtor privado para evitar instanciação da classe utilitária.
     */
    private PersonMapper() {
    }

    /**
     * Converte uma entidade Person para PersonResponse.
     * 
     * @param person entidade Person a ser convertida
     * @return PersonResponse correspondente, ou null se person for null
     */
    public static PersonResponse toResponse(Person person) {
        if (person == null) {
            return null;
        }
        return new PersonResponse(
                person.id(),
                person.name(),
                person.birthDate(),
                person.admissionDate()
        );
    }
}
