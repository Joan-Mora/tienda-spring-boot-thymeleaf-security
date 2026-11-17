package co.frameworks.tienda.config;

import co.frameworks.tienda.model.Producto;
import co.frameworks.tienda.model.Rol;
import co.frameworks.tienda.model.Usuario;
import co.frameworks.tienda.repository.ProductoRepository;
import co.frameworks.tienda.repository.UsuarioRepository;
import java.math.BigDecimal;
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
    private final ProductoRepository productoRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatos() {
        return args -> {
            if (usuarioRepository.count() == 0) {
                crearUsuario("admin", "admin123", Rol.ADMIN);
                crearUsuario("vendedor", "vend123", Rol.VENDEDOR);
                crearUsuario("cliente", "cli123", Rol.CLIENTE);
                log.info("Usuarios de prueba creados: admin/admin123, vendedor/vend123, cliente/cli123");
            }

            if (productoRepository.count() == 0) {
                productoRepository.save(nuevo("Portátil HP", "Intel i5, 16 GB de RAM", "2800000", 8, "Tecnología"));
                productoRepository.save(nuevo("Mouse inalámbrico", "Sensor óptico, batería recargable", "65000", 40, "Tecnología"));
                productoRepository.save(nuevo("Licuadora 600W", "Vaso de vidrio de 1,5 L", "180000", 3, "Hogar"));
                productoRepository.save(nuevo("Camiseta deportiva", "Tela transpirable", "55000", 25, "Ropa"));
                productoRepository.save(nuevo("Balón de fútbol", "Tamaño oficial número 5", "90000", 0, "Deportes"));
                log.info("Productos de prueba cargados: 5 registros");
            }
        };
    }

    private void crearUsuario(String username, String clavePlana, Rol rol) {
        String hash = passwordEncoder.encode(clavePlana);
        usuarioRepository.save(Usuario.crear(username, hash, rol));
    }

    private Producto nuevo(String nombre, String descripcion, String precio, int stock, String categoria) {
        return Producto.crear(nombre, descripcion, new BigDecimal(precio), stock, categoria);
    }
}
