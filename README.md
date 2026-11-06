# Tienda — Spring Boot + Thymeleaf + Spring Security

Aplicación web del laboratorio: autenticación con PostgreSQL (BCrypt), autorización por roles y vistas Thymeleaf con fragmentos.

## Arquitectura

```
co.frameworks.tienda
├── config/          SecurityConfig, DataInitializer
├── controller/      PaginaController (MVC)
├── model/           Usuario, Rol
├── repository/      UsuarioRepository
└── service/         UsuarioDetailsService, RoleHomeResolver
```

- **Controller**: solo enruta y elige vistas.
- **Service**: carga usuarios para Security y resuelve el panel post-login.
- **Config**: reglas de acceso y seed de datos (SRP).
- **Templates**: navbar reutilizable + CSS estático.

## Requisitos

- JDK 17+ (probado con 25/26)
- Maven Wrapper incluido (`mvnw` / `mvnw.cmd`)
- PostgreSQL con base de datos `tienda`

## Configuración de base de datos

1. Crea la base:

```sql
CREATE DATABASE tienda;
```

2. Ajusta la contraseña en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/tienda
spring.datasource.password=${DB_PASSWORD:1234}
```

> En esta máquina PostgreSQL 18 escucha en el puerto **5433** (no 5432).

O ejecuta con variable de entorno:

```powershell
$env:DB_PASSWORD="1234"
.\mvnw.cmd spring-boot:run
```

## Ejecutar

```powershell
cd tienda
.\mvnw.cmd spring-boot:run
```

Abre: http://localhost:8080

## Usuarios de prueba

| Usuario   | Contraseña | Rol      | Panel      |
|-----------|------------|----------|------------|
| admin     | admin123   | ADMIN    | /admin     |
| vendedor  | vend123    | VENDEDOR | /vendedor  |
| cliente   | cli123     | CLIENTE  | /cliente   |

Las contraseñas se guardan con BCrypt (`$2a$...`), nunca en texto plano.

## Matriz de acceso

| Ruta              | Público | CLIENTE | VENDEDOR | ADMIN |
|-------------------|---------|---------|----------|-------|
| `/`, `/login`     | Sí      | Sí      | Sí       | Sí    |
| `/cliente`        | No      | Sí      | No       | Sí    |
| `/vendedor`       | No      | No      | Sí       | Sí    |
| `/admin`          | No      | No      | No       | Sí    |
| `/acceso-denegado`| Sí      | Sí      | Sí       | Sí    |

## Lista de pruebas (rúbrica)

| # | Acción | Esperado | ✓ |
|---|--------|----------|---|
| 1 | Sin sesión: `/` y `/login` | Se ven | |
| 2 | Sin sesión: `/cliente` | Redirige a login | |
| 3 | Login `cliente` | Llega a `/cliente` | |
| 4 | `cliente` abre `/vendedor` | Acceso denegado | |
| 5 | Login `vendedor` | Llega a `/vendedor` | |
| 6 | `vendedor` abre `/admin` | Acceso denegado | |
| 7 | Login `admin` | Llega a `/admin` | |
| 8 | `admin` abre `/cliente` y `/vendedor` | Puede ver ambas | |
| 9 | Logout | Vuelve a `/` y cambia navbar | |
| 10 | Contraseña errónea | Mensaje de error en login | |

## Preguntas de comprensión

1. **Autenticación vs autorización**  
   Autenticación: verificar identidad (login con usuario/clave de BD).  
   Autorización: decidir qué puede hacer (solo ADMIN entra a `/admin`).

2. **Solo `sec:authorize` sin `requestMatchers`**  
   Se ocultarían enlaces en la UI, pero la URL seguiría siendo accesible escribiéndola a mano. La seguridad real debe estar en el servidor.

3. **`th:action` y el 403**  
   Thymeleaf inserta el token CSRF automáticamente. Sin él, Spring Security rechaza el POST.

4. **BCrypt vs texto plano / MD5**  
   BCrypt es unidireccional, con salt y coste configurable. MD5 es rápido e inseguro; texto plano es peor.

5. **`@Controller` vs `@RestController`**  
   `@Controller` resuelve nombres de vista (HTML). `@RestController` serializa el valor de retorno (JSON/texto), por eso verías la palabra `index` en el navegador.

## Entrega

- Carpeta del proyecto **sin** `target/`
- Capturas: paneles cliente/vendedor/admin y acceso denegado
