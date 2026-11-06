package co.frameworks.tienda.model;

/**
 * Roles de negocio de la tienda.
 * Spring Security agregará el prefijo {@code ROLE_} al usar {@code User.roles(...)}.
 */
public enum Rol {
    ADMIN,
    VENDEDOR,
    CLIENTE
}
