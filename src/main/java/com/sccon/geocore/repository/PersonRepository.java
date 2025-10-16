package com.sccon.geocore.repository;

import com.sccon.geocore.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * Interface que define as operações de persistência para a entidade Person.
 * Define o contrato para operações CRUD e outras funcionalidades relacionadas ao repositório.
 */
public interface PersonRepository {

    /**
     * Salva uma pessoa no repositório.
     * 
     * @param person pessoa a ser salva
     * @return pessoa salva
     */
    Person save(Person person);
    
    /**
     * Busca uma pessoa pelo ID.
     * 
     * @param id ID da pessoa
     * @return Optional contendo a pessoa se encontrada
     */
    Optional<Person> findById(Long id);
    
    /**
     * Atualiza uma pessoa existente usando uma função de atualização.
     * 
     * @param id ID da pessoa
     * @param updater função que define como atualizar a pessoa
     * @return pessoa atualizada
     */
    Person update(Long id, UnaryOperator<Person> updater);
    
    /**
     * Retorna todas as pessoas cadastradas.
     * 
     * @return lista de todas as pessoas
     */
    List<Person> findAll();
    
    /**
     * Remove uma pessoa pelo ID.
     * 
     * @param id ID da pessoa a ser removida
     */
    void deleteById(Long id);
    
    /**
     * Verifica se uma pessoa existe pelo ID.
     * 
     * @param id ID da pessoa
     * @return true se a pessoa existe, false caso contrário
     */
    boolean existsById(Long id);
    
    /**
     * Retorna o próximo ID disponível para uma nova pessoa.
     * 
     * @return próximo ID disponível
     */
    Long getNextId();
}
