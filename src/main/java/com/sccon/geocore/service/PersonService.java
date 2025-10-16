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

/**
 * Serviço principal para gerenciamento de pessoas.
 * Fornece operações CRUD e funcionalidades específicas como cálculo de idade e salário.
 */
public class PersonService {
    private final PersonRepository repo;
    private final AgeService ageService;
    private final SalaryService salaryService;

    /**
     * Construtor do serviço de pessoas.
     * 
     * @param repo repositório de pessoas
     * @param clock relógio para cálculos de data
     */
    public PersonService(PersonRepository repo, Clock clock){
        this.repo = repo;
        this.ageService = new AgeService(clock);
        this.salaryService = new SalaryService();
        initializeSampleData();
    }

    /**
     * Inicializa dados de exemplo no repositório.
     * Cria algumas pessoas para demonstração e testes.
     */
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

    /**
     * Cria uma nova pessoa com ID gerado automaticamente.
     * 
     * @param name nome da pessoa
     * @param birthDate data de nascimento
     * @param admissionDate data de admissão
     * @return pessoa criada
     */
    public Person create(String name, LocalDate birthDate, LocalDate admissionDate) {
        Long nextId = repo.getNextId();
        var person = new Person(nextId, name, birthDate, admissionDate);
        return repo.save(person);
    }

    /**
     * Cria uma nova pessoa com ID específico.
     * 
     * @param id ID desejado para a pessoa
     * @param name nome da pessoa
     * @param birthDate data de nascimento
     * @param admissionDate data de admissão
     * @return pessoa criada
     * @throws IllegalArgumentException se já existir uma pessoa com o ID fornecido
     */
    public Person createWithId(Long id, String name, LocalDate birthDate, LocalDate admissionDate) {
        validatePersonDoesNotExist(id);
        Person newPerson = new Person(id, name, birthDate, admissionDate);
        return repo.save(newPerson);
    }

    /**
     * Atualiza todos os dados de uma pessoa.
     * 
     * @param id ID da pessoa
     * @param name novo nome
     * @param birthDate nova data de nascimento
     * @param admissionDate nova data de admissão
     * @return pessoa atualizada
     * @throws NoSuchElementException se a pessoa não for encontrada
     */
    public Person update(Long id, String name, LocalDate birthDate, LocalDate admissionDate) {
        return repo.update(id, current -> new Person(id, name, birthDate, admissionDate));
    }

    /**
     * Atualiza parcialmente os dados de uma pessoa.
     * 
     * @param id ID da pessoa
     * @param name novo nome (opcional)
     * @param birthDate nova data de nascimento (opcional)
     * @param admissionDate nova data de admissão (opcional)
     * @return pessoa atualizada
     * @throws NoSuchElementException se a pessoa não for encontrada
     */
    public Person updatePartial(Long id, Optional<String> name, Optional<LocalDate> birthDate,
                                Optional<LocalDate> admissionDate) {
        return repo.update(id, current -> applyPartialUpdates(current, name, birthDate, admissionDate));
    }

    /**
     * Busca uma pessoa pelo ID.
     * 
     * @param id ID da pessoa
     * @return pessoa encontrada
     * @throws NoSuchElementException se a pessoa não for encontrada
     */
    public Person get(Long id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Pessoa com ID " + id + " não encontrada"));
    }

    /**
     * Retorna todas as pessoas cadastradas, ordenadas por nome.
     * 
     * @return lista de pessoas ordenadas por nome
     */
    public List<Person> findAll() {
        return repo.findAll().stream()
                .sorted(Comparator.comparing(Person::name))
                .toList();
    }

    /**
     * Remove uma pessoa do sistema.
     * 
     * @param id ID da pessoa a ser removida
     * @throws NoSuchElementException se a pessoa não for encontrada
     */
    public void delete(Long id) {
        repo.deleteById(id);
    }

    /**
     * Calcula a idade de uma pessoa.
     * 
     * @param id ID da pessoa
     * @param outputType tipo de saída (days, months, years)
     * @return idade calculada
     * @throws NoSuchElementException se a pessoa não for encontrada
     * @throws IllegalArgumentException se o tipo de saída for inválido
     */
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

    /**
     * Calcula o salário de uma pessoa baseado no tempo de serviço.
     * 
     * @param id ID da pessoa
     * @param outputType tipo de saída (full, min)
     * @return salário calculado
     * @throws NoSuchElementException se a pessoa não for encontrada
     * @throws IllegalArgumentException se o tipo de saída for inválido
     */
    public double calculateSalary(Long id, String outputType) {
        Person person = get(id);
        LocalDate admissionDate = person.admissionDate();
        validateAdmissionDateIsNotNull(admissionDate);
        
        return salaryService.calculateSalary(admissionDate, outputType);
    }

    /**
     * Valida se uma pessoa com o ID fornecido não existe.
     * 
     * @param id ID a ser validado
     * @throws IllegalArgumentException se já existir uma pessoa com o ID
     */
    private void validatePersonDoesNotExist(Long id) {
        if (repo.existsById(id)) {
            throw new IllegalArgumentException("Pessoa com ID " + id + " já existe no sistema");
        }
    }

    /**
     * Aplica atualizações parciais a uma pessoa.
     * 
     * @param current pessoa atual
     * @param name novo nome (opcional)
     * @param birthDate nova data de nascimento (opcional)
     * @param admissionDate nova data de admissão (opcional)
     * @return pessoa com atualizações aplicadas
     */
    private Person applyPartialUpdates(Person current, Optional<String> name, 
                                     Optional<LocalDate> birthDate, Optional<LocalDate> admissionDate) {
        Person updated = current;
        if (name.isPresent()) updated = updated.withName(name.get());
        if (birthDate.isPresent()) updated = updated.withBirthDate(birthDate.get());
        if (admissionDate.isPresent()) updated = updated.withAdmissionDate(admissionDate.get());
        return updated;
    }

    /**
     * Valida se a data de nascimento não é nula.
     * 
     * @param birthDate data de nascimento a ser validada
     * @throws IllegalArgumentException se a data for nula
     */
    private void validateBirthDateIsNotNull(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("Data de nascimento não pode ser nula");
        }
    }

    /**
     * Valida se a data de admissão não é nula.
     * 
     * @param admissionDate data de admissão a ser validada
     * @throws IllegalArgumentException se a data for nula
     */
    private void validateAdmissionDateIsNotNull(LocalDate admissionDate) {
        if (admissionDate == null) {
            throw new IllegalArgumentException("Data de admissão não pode ser nula");
        }
    }
}
