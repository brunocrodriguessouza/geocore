package com.sccon.geocore.config;

import com.sccon.geocore.repository.InMemoryPersonRepository;
import com.sccon.geocore.repository.PersonRepository;
import com.sccon.geocore.service.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class BeansConfig {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public PersonRepository personRepository() {
        return new InMemoryPersonRepository();
    }

    @Bean
    public PersonService personService(PersonRepository repository, Clock clock) {
        return new PersonService(repository, clock);
    }
}
