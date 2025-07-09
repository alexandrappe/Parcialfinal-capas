package com.uca.parcialfinalncapas;

import com.uca.parcialfinalncapas.entities.User;
import com.uca.parcialfinalncapas.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadUsers(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new User(
                        null,
                        "Usuario Normal",
                        "user@soporte.com",
                        encoder.encode("userpass"),
                        "USER"
                ));
                repo.save(new User(
                        null,
                        "Técnico Soporte",
                        "tech@soporte.com",
                        encoder.encode("techpass"),
                        "TECH"
                ));
            }
        };
    }
}
