# Generador de Cuestionarios Online (Backend con Spring Boot)

Una aplicación web interactiva que permite crear, administrar y responder cuestionarios de forma dinámica, con toda la lógica gestionada en el backend.

## Características

- **Backend Potente**: Construido con Spring Boot, Spring Security y Spring Data JPA.
- **Base de Datos PostgreSQL**: Todos los datos (usuarios, quizzes, permisos) se persisten en una base de datos PostgreSQL.
- **Renderizado del Lado del Servidor**: La interfaz de usuario se genera dinámicamente utilizando Thymeleaf.
- **Sistema de Autenticación Completo**:
    - Registro de nuevos usuarios.
    - Inicio de sesión seguro con roles (`ADMIN`, `USER`).
- **Gestión de Quizzes por Usuario**:
    - Los usuarios pueden crear, ver y modificar sus propios quizzes.
    - El panel de administrador (`/admin/quizzes`) está restringido a usuarios con el rol `ADMIN`.
- **Compartir Quizzes**:
    - Los usuarios pueden compartir sus quizzes con otros usuarios registrados.
    - Los usuarios pueden ver y realizar los quizzes que se les han compartido desde su panel principal.

## Tecnologías Utilizadas

- **Backend**:
  - Java 11
  - Spring Boot 2.7.5
  - Spring Security
  - Spring Data JPA
  - Thymeleaf
- **Base de Datos**:
  - PostgreSQL
- **Build Tool**:
  - Maven

## Cómo Usar

1.  **Configurar la Base de Datos**:
    - Asegúrese de tener una base de datos PostgreSQL en funcionamiento.
    - Actualice el archivo `src/main/resources/application.properties` con sus credenciales de la base de datos (URL, usuario, contraseña).

2.  **Ejecutar la Aplicación**:
    - Puede ejecutar la aplicación utilizando su IDE de Java preferido o a través de la línea de comandos con Maven:
      ```bash
      mvn spring-boot:run
      ```

3.  **Acceder a la Aplicación**:
    - Abra su navegador y vaya a `http://localhost:8080`.

## Usuarios de Prueba

La aplicación se inicia con un conjunto de usuarios de prueba para que pueda explorar todas las funcionalidades de inmediato.

-   **Usuario Administrador**:
    -   **Usuario**: `admin`
    -   **Contraseña**: `adminpass`
    -   **Descripción**: Tiene acceso a todas las funcionalidades, incluido el panel de administración para crear y gestionar sus propios quizzes.

-   **Usuario Normal 1 (Propietario de un Quiz)**:
    -   **Usuario**: `user1`
    -   **Contraseña**: `user1pass`
    -   **Descripción**: Este usuario es el propietario de un quiz de prueba llamado "Java Basics Quiz". Ha compartido este quiz con `user2`.

-   **Usuario Normal 2 (Usuario con Quiz Compartido)**:
    -   **Usuario**: `user2`
    -   **Contraseña**: `user2pass`
    -   **Descripción**: Al iniciar sesión, este usuario verá el quiz "Java Basics Quiz" en la sección "Otros Quiz" de su panel, listo para ser realizado.

## Notas de Desarrollo

- El esquema de la base de datos se crea y actualiza automáticamente al iniciar la aplicación gracias a la propiedad `spring.jpa.hibernate.ddl-auto=update`.
- Las contraseñas de los usuarios se almacenan de forma segura utilizando BCrypt.
