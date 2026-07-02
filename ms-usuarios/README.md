# 👤 ms-usuarios

![Java](https://img.shields.io/badge/Java-21+-orange.svg) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-brightgreen.svg) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg) ![OpenFeign](https://img.shields.io/badge/OpenFeign-Enabled-blue.svg) ![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-Producer-red.svg)

> Microservicio encargado de la administración central de usuarios, perfiles y países en la plataforma de torneos universitarios de E-Sports.

---

# 📖 Descripción

`ms-usuarios` es el núcleo de identidad de la plataforma. Proporciona almacenamiento y gestión de la información pública de los usuarios del sistema. Es consumido síncronamente por otros microservicios (a través de llamadas REST declarativas con OpenFeign) para validar la existencia de usuarios, y propaga los cambios de forma asíncrona (a través de Apache Kafka) para mantener sincronizadas las bases de datos de otros servicios en tiempo real.

Este servicio administra:
* **Usuarios:** Identificador central, nombre de usuario (`username`), correo electrónico y fecha de registro.
* **Perfiles públicos:** Información de personalización de los jugadores, incluyendo nicknames, URLs de avatares y país.
* **Países:** Catálogo de países con nombres y códigos simplificados de 3 letras (ISO 3166-1 alpha-3).

---

# 🛠️ Stack Tecnológico

## Backend
* **Java 21**
* **Spring Boot 3.5.14**
* **Spring Data JPA** & **Hibernate**

## Comunicaciones & Descubrimiento
* **Eureka Client** (Registro automático en Eureka Server)
* **Springdoc OpenAPI (Swagger UI)** (Swagger UI local en `/usuarios/swager`)
* **HATEOAS** (Estructuración de respuestas hypermedia)

## Mensajería y Eventos
* **Spring Kafka** (Publicación de eventos de usuarios)

## Persistencia
* **PostgreSQL** (Esquema lógico `users`)

## Utilidades
* **Lombok** & **MapStruct** (Conversión automatizada Entity ↔ DTO)
* **Maven**

---

# 🗄️ Esquema de Base de Datos

Este microservicio utiliza el esquema PostgreSQL `users`. 

```text
users (Schema)
├── usuarios
│   ├── id_usuario (PK)
│   ├── username (Unique)
│   ├── email (Unique)
│   └── creado_el
│
├── perfiles
│   ├── id_perfil (PK)
│   ├── id_usuario (FK, Unique - Relación 1:1)
│   ├── nickname
│   ├── url_avatar
│   └── id_pais (FK)
│
└── paises
    ├── id_pais (PK)
    ├── nombre_pais
    └── codigo_pais (Unique, 3 letras)
```

---

# 🧩 Responsabilidades del Servicio

## Usuarios permite:

* Registrar usuarios
* Consultar usuarios
* Actualizar usuarios
* Eliminar usuarios

## Perfiles permite:

* Asociar perfiles a usuarios
* Gestionar nickname
* Gestionar avatar
* Asociar país

## Países permite:

* Registrar países
* Consultar países
* Administrar códigos simplificados para el pais

---

# 🔄 Comunicación e Integraciones

## 1. Comunicación REST Síncrona (OpenFeign)
Cualquier otro microservicio puede inyectar un cliente Feign para verificar la existencia o recuperar información pública de los usuarios.
```java
@FeignClient(name = "ms-usuarios")
public interface UsuarioClient {
    @GetMapping("/api/v1/usuarios/usuarios/{id}")
    UsuarioDTO getUsuarioById(@PathVariable("id") Long id);
}
```

## 2. Eventos Asíncronos (Apache Kafka)
Para evitar acoplamiento rígido, `ms-usuarios` publica eventos JSON en Kafka cuando ocurren cambios en los datos de los usuarios:
* **Tópico `usuarios-creados`:** Emite un evento `UsuarioCreadoEvent` cuando un usuario es registrado de forma unificada.
* **Tópico `usuarios-actualizados`:** Emite un evento `UsuarioActualizadoEvent` cuando cambia el email del usuario.
* **Tópico `usuarios-eliminados`:** Emite un evento `UsuarioEliminadoEvent` para que otros servicios limpien sus tablas relacionadas.

---

# ⚙️ Configuración y Puerto

* **Puerto local de ejecución:** `9001`
* **Esquema de Base de Datos:** `users`
* **Swagger UI:** `http://localhost:9001/usuarios/swager` (o agregado en Gateway `http://localhost:9000/ms/swagger`)
* **OpenAPI Docs JSON:** `/usuarios/v3/api-docs`

---

# 📌 Endpoints de la API

La URL base para todas las consultas a través del API Gateway es `http://localhost:9000/api/v1/usuarios` (o directo al microservicio en el puerto `9001`). Todas las respuestas REST incorporan enlaces hypermedia **HATEOAS** (`self`, `get`, `update`, `delete`, `all`, etc.).

## 👤 Usuarios (`/usuarios`)
* `POST /usuarios` - Registra un nuevo usuario en la base de datos y publica un evento de creación en Kafka.
* `GET /usuarios` - Retorna la lista de todos los usuarios registrados.
* `GET /usuarios/{id}` - Obtiene los detalles de un usuario específico.
* `PUT /usuarios/{id}` - Actualiza el email y username del usuario (emite evento de actualización en Kafka).
* `DELETE /usuarios/{id}` - Elimina al usuario y su perfil (emite evento de eliminación en Kafka).

## 🎨 Perfiles (`/perfiles`)
* `POST /perfiles` - Crea un perfil público para un usuario.
* `GET /perfiles` - Retorna todos los perfiles configurados.
* `GET /perfiles/{id}` - Obtiene un perfil por su ID.
* `GET /perfiles/usuario/{id}` - Obtiene el perfil asociado a un ID de usuario específico.
* `PUT /perfiles/{id}` - Actualiza el nickname, avatar y país del perfil.
* `DELETE /perfiles/{id}` - Elimina un perfil específico.

## 🌎 Países (`/paises`)
* `POST /paises` - Agrega un nuevo país al catálogo de registro.
* `GET /paises` - Lista los países cargados en el sistema.
* `GET /paises/{id}` - Obtiene un país por su ID.
* `GET /paises/nombre/{nombre}` - Busca un país por su nombre completo.
* `GET /paises/codigo/{codigo}` - Busca un país por su código ISO de 3 letras.
* `PUT /paises/{id}` - Actualiza los datos de un país.
* `DELETE /paises/{id}` - Elimina un país por su ID.

---

# 🚀 Ejecución Manual

Para iniciar este microservicio en consola desde la raíz del proyecto, ejecuta:
```cmd
run-usuarios.bat
```

---

# ⚙️ Configuración Importante

Puerto

```text
9001
```

Schema PostgreSQL

```text
spring:
  jpa:
    properties:
      hibernate:
        default_schema: users
```

---

# 👨‍💻 Autor

* **Ignacio Alvarez** (Desarrollo y diseño de la base de datos, mapeos MapStruct, OpenAPI, enlaces HATEOAS, productores Kafka y cliente Feign).

