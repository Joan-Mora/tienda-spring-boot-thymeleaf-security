# Tienda — Spring Boot + Thymeleaf + Spring Security

Aplicación web del laboratorio: autenticación con PostgreSQL (BCrypt), autorización por roles, catálogo de productos con Model / PathVariable / RequestParam / formularios y patrón Post/Redirect/Get.

## Arquitectura

```
co.frameworks.tienda
├── config/          SecurityConfig, DataInitializer
├── controller/      PaginaController, ProductoController
├── model/           Usuario, Rol, Producto
├── repository/      UsuarioRepository, ProductoRepository
└── service/         UsuarioDetailsService, RoleHomeResolver, ProductoService
```

## Requisitos

- JDK 17+ (probado con 25/26)
- Maven Wrapper incluido (`mvnw` / `mvnw.cmd`)
- PostgreSQL con base de datos `tienda`

## Configuración de base de datos

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/tienda
spring.datasource.password=${DB_PASSWORD:1234}
```

> En esta máquina PostgreSQL 18 escucha en el puerto **5433**.

## Ejecutar

```powershell
cd tienda
.\mvnw.cmd spring-boot:run
```

Abre: http://localhost:8080

## Usuarios de prueba

| Usuario   | Contraseña | Rol      |
|-----------|------------|----------|
| admin     | admin123   | ADMIN    |
| vendedor  | vend123    | VENDEDOR |
| cliente   | cli123     | CLIENTE  |

## Productos

| Ruta | Método | Roles |
|------|--------|-------|
| `/productos` | GET (lista / búsqueda `?nombre=`) | CLIENTE, VENDEDOR, ADMIN |
| `/productos/{id}` | GET detalle | CLIENTE, VENDEDOR, ADMIN |
| `/productos/nuevo` | GET formulario | VENDEDOR, ADMIN |
| `/productos/{id}/editar` | GET formulario | VENDEDOR, ADMIN |
| `/productos/guardar` | POST crear/actualizar | VENDEDOR, ADMIN |
| `/productos/{id}/eliminar` | POST eliminar | VENDEDOR, ADMIN |

El cliente solo consulta. Crear/editar/eliminar están ocultos en la vista y protegidos en `SecurityConfig`.

## Entrega

Repositorio: https://github.com/Joan-Mora/tienda-spring-boot-thymeleaf-security
