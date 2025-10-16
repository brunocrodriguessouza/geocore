package com.sccon.geocore.controller;

import com.sccon.geocore.service.PersonService;
import com.sccon.geocore.web.dto.CreatePersonRequest;
import com.sccon.geocore.web.dto.PersonResponse;
import com.sccon.geocore.web.dto.UpdatePersonRequest;
import com.sccon.geocore.web.mapper.PersonMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST responsável por gerenciar operações relacionadas a pessoas.
 * Fornece endpoints para CRUD completo e operações específicas como cálculo de idade e salário.
 */
@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PersonService personService;

    /**
     * Construtor do controlador.
     * 
     * @param personService serviço de pessoas
     */
    public PeopleController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Retorna todas as pessoas cadastradas no sistema.
     * 
     * @return lista de pessoas ordenadas por nome
     */
    @GetMapping
    public ResponseEntity<List<PersonResponse>> getAllPeople() {
        var people = personService.findAll().stream()
                .map(PersonMapper::toResponse)
                .toList();
        return ResponseEntity.ok(people);
    }

    /**
     * Retorna uma pessoa específica pelo ID.
     * 
     * @param id ID da pessoa
     * @return dados da pessoa
     */
    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> getPersonById(@PathVariable Long id) {
        var person = personService.get(id);
        return ResponseEntity.ok(PersonMapper.toResponse(person));
    }

    /**
     * Cria uma nova pessoa com ID gerado automaticamente.
     * 
     * @param request dados da pessoa a ser criada
     * @return dados da pessoa criada
     */
    @PostMapping
    public ResponseEntity<PersonResponse> createPerson(@Valid @RequestBody CreatePersonRequest request) {
        var person = personService.create(request.name(), request.birthDate(), request.admissionDate());
        return ResponseEntity.ok(PersonMapper.toResponse(person));
    }

    /**
     * Cria uma nova pessoa com ID específico.
     * 
     * @param id ID desejado para a pessoa
     * @param request dados da pessoa a ser criada
     * @return dados da pessoa criada
     */
    @PostMapping("/{id}")
    public ResponseEntity<PersonResponse> createPersonWithId(
            @PathVariable Long id,
            @Valid @RequestBody CreatePersonRequest request) {
        var person = personService.createWithId(id, request.name(), request.birthDate(), request.admissionDate());
        return ResponseEntity.ok(PersonMapper.toResponse(person));
    }

    /**
     * Atualiza todos os dados de uma pessoa.
     * 
     * @param id ID da pessoa
     * @param request novos dados da pessoa
     * @return dados atualizados da pessoa
     */
    @PutMapping("/{id}")
    public ResponseEntity<PersonResponse> updatePerson(
            @PathVariable Long id,
            @Valid @RequestBody CreatePersonRequest request) {
        var person = personService.update(id, request.name(), request.birthDate(), request.admissionDate());
        return ResponseEntity.ok(PersonMapper.toResponse(person));
    }

    /**
     * Atualiza parcialmente os dados de uma pessoa.
     * 
     * @param id ID da pessoa
     * @param request dados parciais para atualização
     * @return dados atualizados da pessoa
     */
    @PatchMapping("/{id}")
    public ResponseEntity<PersonResponse> updatePersonPartially(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePersonRequest request) {
        var person = personService.updatePartial(id, request.name(), request.birthDate(), request.admissionDate());
        return ResponseEntity.ok(PersonMapper.toResponse(person));
    }

    /**
     * Remove uma pessoa do sistema.
     * 
     * @param id ID da pessoa a ser removida
     * @return resposta vazia com status 204
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Calcula a idade de uma pessoa.
     * 
     * @param id ID da pessoa
     * @param output tipo de saída (days, months, years)
     * @return idade calculada
     */
    @GetMapping("/{id}/age")
    public ResponseEntity<Long> getPersonAge(
            @PathVariable Long id,
            @RequestParam String output) {
        var age = personService.calculateAge(id, output);
        return ResponseEntity.ok(age);
    }

    /**
     * Calcula o salário de uma pessoa baseado no tempo de serviço.
     * 
     * @param id ID da pessoa
     * @param output tipo de saída (full, min)
     * @return salário calculado
     */
    @GetMapping("/{id}/salary")
    public ResponseEntity<Double> getPersonSalary(
            @PathVariable Long id,
            @RequestParam String output) {
        var salary = personService.calculateSalary(id, output);
        return ResponseEntity.ok(salary);
    }
}
