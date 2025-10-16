package com.sccon.geocore.repository;

import com.sccon.geocore.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public interface PersonRepository {

    Person save(Person person);
    Optional<Person> findById(Long id);
    Person update(Long id, UnaryOperator<Person> updater);
    List<Person> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    Long getNextId();
}
