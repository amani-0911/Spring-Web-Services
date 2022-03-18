package org.sid.springwebservice;

import org.sid.springwebservice.entities.Compte;
import org.sid.springwebservice.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class SpringWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CompteRepository compteRepository){
        return args->{
            compteRepository.save(new Compte(null,70000,new Date(),"active"));
            compteRepository.save(new Compte(null,4770,new Date(),"active"));
            compteRepository.save(new Compte(null,3000,new Date(),"bloched"));
        };
    }
}
