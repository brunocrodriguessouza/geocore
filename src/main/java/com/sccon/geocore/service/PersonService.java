package com.sccon.geocore.service;

import com.sccon.geocore.model.Person;
import com.sccon.geocore.repository.PersonRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class PersonService {
    private final PersonRepository repo;
    private final AgeService ageService;
    private final SalaryService salaryService;

    public PersonService(PersonRepository repo, Clock clock){
        this.repo = repo;
        this.ageService = new AgeService(clock);
        this.salaryService = new SalaryService();
        initializeSampleData();
    }

    private void initializeSampleData() {
        Person jose = new Person(1L, "José da Silva", 
                LocalDate.of(2000, 4, 6), 
                LocalDate.of(2020, 5, 10));
        
        Person maria = new Person(2L, "Maria Santos", 
                LocalDate.of(1995, 8, 15), 
                LocalDate.of(2019, 3, 20));
        
        Person joao = new Person(3L, "João Oliveira", 
                LocalDate.of(1988, 12, 3), 
                LocalDate.of(2021, 1, 15));
        
        repo.save(jose);
        repo.save(maria);
        repo.save(joao);
    }

    public Person create(String name, LocalDate birthDate, LocalDate admissionDate) {
        Long nextId = repo.getNextId();
        var person = new Person(nextId, name, birthDate, admissionDate);
        return repo.save(person);
    }

    public Person createWithId(Long id, String name, LocalDate birthDate, LocalDate admissionDate) {
        validatePersonDoesNotExist(id);
        Person newPerson = new Person(id, name, birthDate, admissionDate);
        return repo.save(newPerson);
    }

    public Person update(Long id, String name, LocalDate birthDate, LocalDate admissionDate) {
        return repo.update(id, current -> new Person(id, name, birthDate, admissionDate));
    }

    public Person updatePartial(Long id, Optional<String> name, Optional<LocalDate> birthDate,
                                Optional<LocalDate> admissionDate) {
        return repo.update(id, current -> applyPartialUpdates(current, name, birthDate, admissionDate));
    }

    public Person get(Long id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Pessoa com ID " + id + " não encontrada"));
    }

    public List<Person> findAll() {
        return repo.findAll().stream()
                .sorted(Comparator.comparing(Person::name))
                .toList();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public long calculateAge(Long id, String outputType) {
        Person person = get(id);
        LocalDate birthDate = person.birthDate();
        validateBirthDateIsNotNull(birthDate);
        
        return switch (outputType.toLowerCase()) {
            case "days" -> ChronoUnit.DAYS.between(birthDate, LocalDate.now());
            case "months" -> ChronoUnit.MONTHS.between(birthDate, LocalDate.now());
            case "years" -> ageService.diff(birthDate).years();
            default -> throw new IllegalArgumentException("Tipo de saída inválido: " + outputType + 
                    ". Valores aceitos: days, months, years");
        };
    }

    public double calculateSalary(Long id, String outputType) {
        Person person = get(id);
        LocalDate admissionDate = person.admissionDate();
        validateAdmissionDateIsNotNull(admissionDate);
        
        return salaryService.calculateSalary(admissionDate, outputType);
    }

    private void validatePersonDoesNotExist(Long id) {
        if (repo.existsById(id)) {
            throw new IllegalArgumentException("Pessoa com ID " + id + " já existe no sistema");
        }
    }

    private Person applyPartialUpdates(Person current, Optional<String> name, 
                                     Optional<LocalDate> birthDate, Optional<LocalDate> admissionDate) {
        Person updated = current;
        if (name.isPresent()) updated = updated.withName(name.get());
        if (birthDate.isPresent()) updated = updated.withBirthDate(birthDate.get());
        if (admissionDate.isPresent()) updated = updated.withAdmissionDate(admissionDate.get());
        return updated;
    }

    private void validateBirthDateIsNotNull(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("Data de nascimento não pode ser nula");
        }
    }

    private void validateAdmissionDateIsNotNull(LocalDate admissionDate) {
        if (admissionDate == null) {
            throw new IllegalArgumentException("Data de admissão não pode ser nula");
        }
    }
}
