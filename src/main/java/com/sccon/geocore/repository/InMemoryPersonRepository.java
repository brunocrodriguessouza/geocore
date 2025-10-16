package com.sccon.geocore.repository;

import com.sccon.geocore.model.Person;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;

public class InMemoryPersonRepository implements PersonRepository {

    private final Map<Long, Person> personStore = new ConcurrentHashMap<>();

    @Override
    public Person save(Person person) {
        personStore.put(person.id(), person);
        return person;
    }

    @Override
    public Optional<Person> findById(Long id) {
        return Optional.ofNullable(personStore.get(id));
    }

    @Override
    public Person update(Long id, UnaryOperator<Person> updater) {
        return personStore.compute(id, (key, existingPerson) -> {
            if (existingPerson == null) {
                throw new java.util.NoSuchElementException("Person with id %s not found".formatted(id));
            }
            return updater.apply(existingPerson);
        });
    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(personStore.values());
    }

    @Override
    public void deleteById(Long id) {
        if (!personStore.containsKey(id)) {
            throw new java.util.NoSuchElementException("Person with id %s not found".formatted(id));
        }
        personStore.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return personStore.containsKey(id);
    }

    @Override
    public Long getNextId() {
        return personStore.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }
}
