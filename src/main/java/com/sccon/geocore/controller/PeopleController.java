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

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PersonService personService;

    public PeopleController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<PersonResponse>> getAllPeople() {
        var people = personService.findAll().stream()
                .map(PersonMapper::toResponse)
                .toList();
        return ResponseEntity.ok(people);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> getPersonById(@PathVariable Long id) {
        var person = personService.get(id);
        return ResponseEntity.ok(PersonMapper.toResponse(person));
    }

    @PostMapping
    public ResponseEntity<PersonResponse> createPerson(@Valid @RequestBody CreatePersonRequest request) {
        var person = personService.create(request.name(), request.birthDate(), request.admissionDate());
        return ResponseEntity.ok(PersonMapper.toResponse(person));
    }

    @PostMapping("/{id}")
    public ResponseEntity<PersonResponse> createPersonWithId(
            @PathVariable Long id,
            @Valid @RequestBody CreatePersonRequest request) {
        var person = personService.createWithId(id, request.name(), request.birthDate(), request.admissionDate());
        return ResponseEntity.ok(PersonMapper.toResponse(person));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponse> updatePerson(
            @PathVariable Long id,
            @Valid @RequestBody CreatePersonRequest request) {
        var person = personService.update(id, request.name(), request.birthDate(), request.admissionDate());
        return ResponseEntity.ok(PersonMapper.toResponse(person));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PersonResponse> updatePersonPartially(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePersonRequest request) {
        var person = personService.updatePartial(id, request.name(), request.birthDate(), request.admissionDate());
        return ResponseEntity.ok(PersonMapper.toResponse(person));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/age")
    public ResponseEntity<Long> getPersonAge(
            @PathVariable Long id,
            @RequestParam String output) {
        var age = personService.calculateAge(id, output);
        return ResponseEntity.ok(age);
    }

    @GetMapping("/{id}/salary")
    public ResponseEntity<Double> getPersonSalary(
            @PathVariable Long id,
            @RequestParam String output) {
        var salary = personService.calculateSalary(id, output);
        return ResponseEntity.ok(salary);
    }
}
