package com.cafeteca.nebula.config;

import com.cafeteca.nebula.model.Cliente;
import com.cafeteca.nebula.model.Libro;
import com.cafeteca.nebula.model.Usuario;
import com.cafeteca.nebula.repository.ClienteRepository;
import com.cafeteca.nebula.repository.LibroRepository;
import com.cafeteca.nebula.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(
            UsuarioRepository usuarioRepository,
            LibroRepository libroRepository,
            ClienteRepository clienteRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            try {
                // 1. Inicializar usuario Administrador
                if (usuarioRepository.findByUsername("admin").isEmpty()) {
                    Usuario admin = new Usuario();
                    admin.setUsername("admin");
                    admin.setPassword(passwordEncoder.encode("admin123"));
                    admin.setRol("ROLE_ADMIN");
                    usuarioRepository.save(admin);
                    System.out.println(">>> Usuario administrador inicial creado: admin / admin123 <<<");
                }

                // 2. Inicializar Libros (solo si la tabla está vacía)
                if (libroRepository.count() == 0) {
                    libroRepository.saveAll(List.of(
                            crearLibro("Don Quijote de la Mancha", "Miguel de Cervantes", "978-8424922405", true),
                            crearLibro("Frankenstein", "Mary Shelley", "978-0141439471", true),
                            crearLibro("La casa de los espíritus", "Isabel Allende", "978-0307474728", true),
                            crearLibro("Los miserables", "Victor Hugo", "978-8448831301", true),
                            crearLibro("Moby Dick", "Herman Melville", "978-1503280786", true),
                            crearLibro("Orgullo y prejuicio", "Jane Austen", "978-8497940801", true),
                            crearLibro("Crónica de una muerte anunciada", "Gabriel García Márquez", "978-1400034956", true),
                            crearLibro("El principito", "Antoine de Saint-Exupéry", "978-0156013987", true),
                            crearLibro("Rayuela", "Julio Cortázar", "978-8437604572", true),
                            crearLibro("1984", "George Orwell", "978-0451524935", true)
                    ));
                    System.out.println(">>> Carga inicial de libros realizada con éxito <<<");
                }

                // 3. Inicializar Clientes (solo si la tabla está vacía)
                if (clienteRepository.count() == 0) {
                    clienteRepository.saveAll(List.of(
                            crearCliente("Vania Torres", "vania@email.com", "8110000000"),
                            crearCliente("Álvaro Sierra", "alvaro.sierra@example.com", "8111002233"),
                            crearCliente("Carlos Souza", "carlos.souza@example.com", "8112113344"),
                            crearCliente("Cristina Negrete", "cristina.negrete@example.com", "8113224455"),
                            crearCliente("Sonia Sánchez", "sonia.sanchez@example.com", "8114335566"),
                            crearCliente("Ingrid Rivera", "ingrid.rivera@example.com", "8115446677"),
                            crearCliente("Mateo Fernández", "mateo.fernandez@example.com", "8116557788"),
                            crearCliente("Lucía Mendoza", "lucia.mendoza@example.com", "8117668899"),
                            crearCliente("Gabriel Ortiz", "gabriel.ortiz@example.com", "8118779900"),
                            crearCliente("Elena Navarro", "elena.navarro@example.com", "8119880011"),
                            crearCliente("Elizabeth Taylor", "elizabeth.y@example.com", "8893446589"),
                            crearCliente("Diego Salazar", "diego.salazar@example.com", "8119998877")
                    ));
                    System.out.println(">>> Carga inicial de clientes realizada con éxito <<<");
                }

            } catch (Exception e) {
                System.out.println(">>> Inicialización de datos omitida o en proceso de creación de tablas <<<");
            }
        };
    }

    private Libro crearLibro(String titulo, String autor, String isbn, boolean disponible) {
        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setIsbn(isbn);
        libro.setDisponible(disponible);
        return libro;
    }

    private Cliente crearCliente(String nombre, String email, String telefono) {
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        return cliente;
    }
}