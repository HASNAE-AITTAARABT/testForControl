package org.example;


import org.example.entities.Patient;
import org.example.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
//ws41 et client41

@SpringBootApplication
public class TestingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestingApplication.class,args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {
            patientRepository.save(new Patient(null,"hassan",new Date(),false,122));
            patientRepository.save(new Patient(null,"mohamed",new Date(),true,321));
            patientRepository.save(new Patient(null,"yasmin",new Date(),true,165));
            patientRepository.save(new Patient(null,"hanae",new Date(),false,132));
            patientRepository.findAll().forEach(patient -> {
                System.out.println(patient.getNom());
            });
        };
    }
    //passwordEncoder:
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}