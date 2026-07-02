# 🔒 ms-autenticaciones

![Java](https://img.shields.io/badge/Java-21+-orange.svg) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-brightgreen.svg) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg) ![OpenFeign](https://img.shields.io/badge/OpenFeign-Enabled-blue.svg) ![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-Consumer-red.svg)

> Microservicio encargado de la seguridad lógica, autenticación de usuarios (JWT), hasheo de credenciales (BCrypt) y gestión de roles en el ecosistema.

---

# 📖 Descripción

`ms-autenticaciones` protege la integridad del sistema. Es responsable del ciclo de registro de cuentas, validación de logins y otorgamiento de roles de seguridad. 

Este servicio proporciona:
1. **Registro unificado (`/registro`):** Se comunica síncronamente con `ms-usuarios` via Feign para crear al usuario en el sistema. Al recibir confirmación exitosa, hashea la contraseña usando BCrypt, asocia el rol por defecto (`ROLE_PLAYER`) y genera inmediatamente un token JWT firmado para el usuario.
2. **Autenticación segura (`/login`):** Valida contraseñas encriptadas con BCrypt y contraseñas de legado (`HASH_...` para mocks de base de datos) y retorna un token JWT que contiene el email, roles y el ID de usuario centralizado.
3. **Control de Accesos (Roles):** Maneja roles a nivel de sistema (`ROLE_ADMIN`, `ROLE_PLAYER`, `ROLE_REFEREE`, `ROLE_ORGANIZER`).
4. **Sincronización en Tiempo Real (Kafka):** Escucha eventos de `ms-usuarios` para actualizar o eliminar credenciales de manera asíncrona si el usuario central es editado o removido.

---

# 🛠️ Stack Tecnológico

## Backend & Seguridad
* **Java 21**
* **Spring Boot 3.5.14**
* **Spring Security** & **BCrypt PasswordEncoder**
* **JJWT** (Java JWT para tokens autogestionados)

## Descubrimiento y Documentación
* **Eureka Client** (Descubrimiento dinámico)
* **Springdoc OpenAPI (Swagger UI)** (Swagger UI local en `/autenticaciones/swager`)
* **HATEOAS** (Enlaces en DTOs de respuesta)

## Mensajería y Eventos
* **Spring Kafka** (Consumidor de tópicos de usuarios)

## Persistencia
* **PostgreSQL** (Esquema lógico `auth`)

## Utilidades
* **Lombok** & **MapStruct**
* **Maven**

---

# 🗄️ Esquema de Base de Datos

Este microservicio utiliza el esquema PostgreSQL `auth`.

```text
auth (Schema)
├── auth_usuarios
│   ├── id_usuario (PK - Mapea 1:1 al id de usuarios centralizado)
│   ├── email (Unique)
│   ├── hash_contrasenia
│   └── bloqueada (Boolean)
│
├── roles
│   ├── id_rol (PK)
│   └── nombre_rol (Unique)
│
└── roles_usuarios (Tabla Intermedia N:M)
    ├── id_usuario (FK)
    └── id_rol (FK)
```

---

# 🧩 Responsabilidades del Servicio

## Credenciales permite:

* Registrar credenciales
* Actualizar credenciales
* Eliminar credenciales
* Validar emails únicos
* Asociar roles a usuarios

## Roles permite:

* Crear roles
* Consultar roles

## Ejemplos:

* ROLE_ADMIN
* ROLE_PLAYER
* ROLE_REFEREE
* ROLE_ORGANIZER

---

# 🔄 Comunicación e Integraciones

## 1. Clientes Síncronos (Feign)
Consume `ms-usuarios` para:
* Crear el usuario en la base de datos de usuarios durante el `/registro`.
* Validar que el usuario exista antes de crear credenciales directamente con `/usuarios` (POST).

```java
@FeignClient(name = "ms-usuarios")
public interface UsuarioClient {
    @GetMapping("/api/v1/usuarios/usuarios/{id}")
    UsuarioDTO getUsuarioById(@PathVariable("id") Long id);

    @PostMapping("/api/v1/usuarios/usuarios")
    UsuarioDTO createUsuario(@RequestBody UsuarioCreateDTO request);
}
```

## 2. Consumidor Asíncrono (Kafka Listeners)
Para mantener la integridad referencial sin llamadas síncronas costosas al borrar o modificar usuarios, `ms-autenticaciones` consume:
* **Tópico `usuarios-actualizados`:** Sincroniza inmediatamente el email del usuario en la tabla `auth.auth_usuarios`.
* **Tópico `usuarios-eliminados`:** Remueve las credenciales y relaciones del usuario eliminado en cascada local.

---

# 📂 Estructura Principal

```text
ms-autenticaciones/
├── client/
├── controller/
├── service/
├── repository/
├── mapper/
├── model/
├── dtos/
└── resources/
```

---

# ⚙️ Configuración y Puerto

* **Puerto local de ejecución:** `9002`
* **Esquema de Base de Datos:** `auth`
* **Swagger UI:** `http://localhost:9002/autenticaciones/swager` (o agregado en Gateway `http://localhost:9000/ms/swagger`)
* **OpenAPI Docs JSON:** `/autenticaciones/v3/api-docs`

---

# 📌 Endpoints de la API

La URL base para las consultas a través del API Gateway es `http://localhost:9000/api/v1/autenticaciones` (o directo en el puerto `9002`). El Gateway también reescribe convenientemente `/api/v1/auth/**` hacia este servicio. Las respuestas REST implementan **HATEOAS**.

## 🔑 Autenticación y Registro (`/registro` y `/login`)
* `POST /registro` - Crea el usuario en `ms-usuarios` via Feign, registra sus credenciales hasheadas con BCrypt y retorna un token JWT firmado de inicio de sesión inmediato.
* `POST /login` - Autentica a un usuario y genera su token JWT de sesión.

## 👤 Credenciales (`/usuarios`)
* `POST /usuarios` - Genera credenciales de autenticación para un usuario existente (previa validación via Feign).
* `GET /usuarios/{id}` - Obtiene los datos de credenciales de un usuario (excluye contraseña).
* `PUT /usuarios/{id}` - Actualiza el correo electrónico y/o la contraseña de un usuario.
* `DELETE /usuarios/{id}` - Elimina las credenciales de acceso de un usuario.

## 🏷️ Roles (`/roles`)
* `POST /roles` - Registra un nuevo rol en el sistema.
* `GET /roles` - Lista todos los roles existentes (`ROLE_ADMIN`, `ROLE_PLAYER`, etc.).
* `GET /roles/{id}` - Obtiene un rol específico por su ID.
* `PUT /roles/{id}` - Modifica el nombre de un rol.
* `DELETE /roles/{id}` - Elimina un rol específico.

---

# 🚀 Ejecución Manual

Para iniciar este microservicio en consola desde la raíz del proyecto, ejecuta:
```cmd
run-autenticaciones.bat
```
> *Nota: Requiere que `postgres-db`, `kafka` y `ms-usuarios` estén levantados previamente.*

---

# 👨‍💻 Autor

* **Ignacio Alvarez** (Desarrollo del flujo de autenticación, hasheo BCrypt, integración JWT, mapeos, OpenAPI, enlaces HATEOAS, consumidores Kafka y cliente Feign).