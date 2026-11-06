package co.frameworks.tienda.service;

import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * Determina el panel de inicio según el rol autenticado.
 */
@Component
public class RoleHomeResolver {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_VENDEDOR = "ROLE_VENDEDOR";

    public String resolveHomePath(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (hasAuthority(authorities, ROLE_ADMIN)) {
            return "/admin";
        }
        if (hasAuthority(authorities, ROLE_VENDEDOR)) {
            return "/vendedor";
        }
        return "/cliente";
    }

    private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String role) {
        return authorities.stream().anyMatch(authority -> role.equals(authority.getAuthority()));
    }
}
