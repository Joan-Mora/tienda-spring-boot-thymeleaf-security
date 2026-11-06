package co.frameworks.tienda.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class RoleHomeResolverTest {

    private final RoleHomeResolver resolver = new RoleHomeResolver();

    @Test
    void adminVaAlPanelAdmin() {
        var auth = token("ROLE_ADMIN");
        assertEquals("/admin", resolver.resolveHomePath(auth));
    }

    @Test
    void vendedorVaAlPanelVendedor() {
        var auth = token("ROLE_VENDEDOR");
        assertEquals("/vendedor", resolver.resolveHomePath(auth));
    }

    @Test
    void clienteVaAlPanelCliente() {
        var auth = token("ROLE_CLIENTE");
        assertEquals("/cliente", resolver.resolveHomePath(auth));
    }

    private UsernamePasswordAuthenticationToken token(String role) {
        return new UsernamePasswordAuthenticationToken(
                "user",
                "n/a",
                List.of(new SimpleGrantedAuthority(role))
        );
    }
}
