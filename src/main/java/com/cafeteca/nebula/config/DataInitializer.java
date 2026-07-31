package com.cafeteca.nebula.config;

import com.cafeteca.nebula.model.Usuario;
import com.cafeteca.nebula.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            try {
                if (usuarioRepository.findByUsername("admin").isEmpty()) {
                    Usuario admin = new Usuario();
                    admin.setUsername("admin");
                    admin.setPassword(passwordEncoder.encode("admin123"));
                    admin.setRol("ROLE_ADMIN");
                    usuarioRepository.save(admin);
                    System.out.println(">>> Usuario administrador inicial creado: admin / admin123 <<<");
                }
            } catch (Exception e) {
                System.out.println(">>> Inicialización de datos omitida o en proceso de creación de tablas <<<");
            }
        };
    }
}