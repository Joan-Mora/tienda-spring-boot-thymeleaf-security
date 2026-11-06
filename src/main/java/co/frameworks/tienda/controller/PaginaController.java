package co.frameworks.tienda.controller;

import co.frameworks.tienda.service.RoleHomeResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PaginaController {

    private final RoleHomeResolver roleHomeResolver;

    @GetMapping("/")
    public String inicio() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cliente")
    public String cliente() {
        return "cliente";
    }

    @GetMapping("/vendedor")
    public String vendedor() {
        return "vendedor";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "acceso-denegado";
    }

    @GetMapping("/redirigir")
    public String redirigir(Authentication authentication) {
        return "redirect:" + roleHomeResolver.resolveHomePath(authentication);
    }
}
