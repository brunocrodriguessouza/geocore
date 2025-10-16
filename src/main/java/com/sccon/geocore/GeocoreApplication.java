package com.sccon.geocore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Geocore.
 * Esta é a classe de entrada que inicializa a aplicação Spring Boot.
 */
@SpringBootApplication
public class GeocoreApplication {

	/**
	 * Método principal que inicia a aplicação Spring Boot.
	 * 
	 * @param args argumentos da linha de comando
	 */
	public static void main(String[] args) {
		SpringApplication.run(GeocoreApplication.class, args);
	}

}
