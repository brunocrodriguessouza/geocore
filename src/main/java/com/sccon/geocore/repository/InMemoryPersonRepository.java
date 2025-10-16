package com.sccon.geocore.repository;

import com.sccon.geocore.model.Person;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;

/**
 * Implementação em memória do repositório de pessoas.
 * Esta implementação usa um ConcurrentHashMap para armazenar as pessoas
 * e é adequada para desenvolvimento e testes.
 */
public class InMemoryPersonRepository implements PersonRepository {

    private final Map<Long, Person> personStore = new ConcurrentHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Person save(Person person) {
        personStore.put(person.id(), person);
        return person;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Person> findById(Long id) {
        return Optional.ofNullable(personStore.get(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Person update(Long id, UnaryOperator<Person> updater) {
        return personStore.compute(id, (key, existingPerson) -> {
            if (existingPerson == null) {
                throw new java.util.NoSuchElementException("Person with id %s not found".formatted(id));
            }
            return updater.apply(existingPerson);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Person> findAll() {
        return new ArrayList<>(personStore.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(Long id) {
        if (!personStore.containsKey(id)) {
            throw new java.util.NoSuchElementException("Person with id %s not found".formatted(id));
        }
        personStore.remove(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsById(Long id) {
        return personStore.containsKey(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getNextId() {
        return personStore.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }
}
