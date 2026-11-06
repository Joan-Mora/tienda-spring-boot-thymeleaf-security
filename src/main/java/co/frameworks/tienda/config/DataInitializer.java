package co.frameworks.tienda.config;

import co.frameworks.tienda.model.Rol;
import co.frameworks.tienda.model.Usuario;
import co.frameworks.tienda.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatos() {
        return args -> {
            if (usuarioRepository.count() > 0) {
                return;
            }

            crearUsuario("admin", "admin123", Rol.ADMIN);
            crearUsuario("vendedor", "vend123", Rol.VENDEDOR);
            crearUsuario("cliente", "cli123", Rol.CLIENTE);

            log.info("Usuarios de prueba creados: admin/admin123, vendedor/vend123, cliente/cli123");
        };
    }

    private void crearUsuario(String username, String clavePlana, Rol rol) {
        String hash = passwordEncoder.encode(clavePlana);
        usuarioRepository.save(Usuario.crear(username, hash, rol));
    }
}
