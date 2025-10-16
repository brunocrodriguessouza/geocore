package com.sccon.geocore.config;

import com.sccon.geocore.repository.InMemoryPersonRepository;
import com.sccon.geocore.repository.PersonRepository;
import com.sccon.geocore.service.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

/**
 * Classe de configuração que define os beans do Spring.
 * Esta classe é responsável por configurar e disponibilizar as dependências
 * necessárias para o funcionamento da aplicação.
 */
@Configuration
public class BeansConfig {

    /**
     * Cria um bean Clock configurado para UTC.
     * 
     * @return Clock configurado para UTC
     */
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    /**
     * Cria um bean PersonRepository usando implementação em memória.
     * 
     * @return instância de PersonRepository
     */
    @Bean
    public PersonRepository personRepository() {
        return new InMemoryPersonRepository();
    }

    /**
     * Cria um bean PersonService com suas dependências.
     * 
     * @param repository repositório de pessoas
     * @param clock relógio para cálculos de data
     * @return instância de PersonService
     */
    @Bean
    public PersonService personService(PersonRepository repository, Clock clock) {
        return new PersonService(repository, clock);
    }
}
