# 🛡️ ms-equipos

![Java](https://img.shields.io/badge/Java-21+-orange.svg) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-brightgreen.svg) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg) ![OpenFeign](https://img.shields.io/badge/OpenFeign-Enabled-blue.svg) ![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-Consumer-red.svg)

> Microservicio encargado de la administración de equipos competitivos, gestión de miembros (rosters) y definición de roles internos del equipo.

---

# 📖 Descripción

`ms-equipos` gestiona la creación de escuadras y plantillas de jugadores dentro del sistema de torneos. 

Este servicio proporciona:
1. **Gestión de Equipos:** Registro de equipos competitivos y fecha de fundación.
2. **Administración de Miembros (`/miembros`):** Permite asociar jugadores a equipos con un rol específico.
   * **Regla de Negocio:** Un usuario solo puede pertenecer a un único equipo.
   * **Validación de Identidad:** Cuando se intenta agregar un miembro, el servicio realiza una llamada REST síncrona (via Feign) a `ms-usuarios` para certificar la existencia del usuario.
3. **Roles Internos:** Definición de la función de cada miembro dentro del equipo (ej. Capitán, Jugador Titular, Jugador Suplente, Coach, Analista).
4. **Sincronización de Datos (Kafka):** Consume eventos del tópico `usuarios-creados` para almacenar localmente las IDs de usuarios en una tabla de referencia rápida, y del tópico `usuarios-eliminados` para remover automáticamente a un usuario de su equipo y su catálogo de referencias local si su cuenta central es eliminada.

---

# 🛠️ Stack Tecnológico

## Backend
* **Java 21**
* **Spring Boot 3.5.14**
* **Spring Data JPA** & **Hibernate**

## Descubrimiento y Documentación
* **Eureka Client** (Registro de servicios)
* **Springdoc OpenAPI (Swagger UI)** (Swagger UI en `/equipos/swager`)
* **HATEOAS** (Enlaces estructurados de navegación REST)

## Mensajería y Eventos
* **Spring Kafka** (Consumo de eventos de usuarios)

## Persistencia
* **PostgreSQL** (Esquema lógico `team`)

## Utilidades
* **Lombok** & **MapStruct**
* **Maven**

---

# 🗄️ Esquema de Base de Datos

Este microservicio utiliza el esquema PostgreSQL `team`.

```text
team (Schema)
├── equipos
│   ├── id_equipo (PK)
│   ├── nombre_equipo (Unique)
│   └── fundado_el
│
├── roles_equipo
│   ├── id_rol_equipo (PK)
│   └── nombre_rol_equipo (Unique)
│
├── usuarios (Tabla Caché de Referencia)
│   └── id_usuario (PK)
│
└── miembros_equipos
    ├── id_miembro_equipo (PK)
    ├── id_usuario (FK, Unique - Garantiza que un usuario esté en un solo equipo)
    ├── id_equipo (FK)
    └── id_rol_equipo (FK)
```

---

# 🧩 Responsabilidades del Servicio
## Equipos permite:
* Crear equipos competitivos
* Actualizar información
* Eliminar equipos
* Consultar rosters
## miembros permite:
* Agregar jugadores
* Eliminar integrantes
* Validar usuarios existentes

## Roles permite:
* Crear roles Internos
* Gestionar roles internos

## Ejemplos:
* Capitán
* Jugador Titular
* Jugador Suplente
* Coach
* Analista

---

# 🔄 Comunicación e Integraciones

## 1. Validación de Usuarios (Feign)
Se comunica síncronamente con `ms-usuarios` para verificar la existencia del usuario al registrar un miembro:
```java
@FeignClient(name = "ms-usuarios")
public interface UsuarioClient {
    @GetMapping("/api/v1/usuarios/usuarios/{id}")
    UsuarioDTO getUsuarioById(@PathVariable("id") Long id);
}
```

## 2. Consumidor Asíncrono (Kafka Listeners)
Para responder a los eventos de usuario de forma desasociada:
* **Tópico `usuarios-creados`:** Inserta la ID del usuario en la tabla caché local `team.usuarios` para autorizar su futuro registro en equipos.
* **Tópico `usuarios-eliminados`:** Elimina al usuario de la tabla caché local `team.usuarios` y limpia sus registros de membresía en `team.miembros_equipos` de forma automática.

---

# 📂 Estructura Principal
``` text
ms-equipos/
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

* **Puerto local de ejecución:** `9005`
* **Esquema de Base de Datos:** `team`
* **Swagger UI:** `http://localhost:9005/equipos/swager` (o agregado en Gateway `http://localhost:9000/ms/swagger`)
* **OpenAPI Docs JSON:** `/equipos/v3/api-docs`

---

# 📌 Endpoints de la API

La URL base para las consultas a través del API Gateway es `http://localhost:9000/api/v1/equipos` (o directo en el puerto `9005`). Todas las respuestas REST están enriquecidas con enlaces **HATEOAS**.

## 🛡️ Equipos (`/equipos`)
* `POST /equipos` - Registra un nuevo equipo competitivo.
* `GET /equipos` - Retorna todos los equipos registrados.
* `GET /equipos/{id}` - Obtiene los detalles de un equipo específico.
* `PUT /equipos/{id}` - Modifica los datos de un equipo.
* `DELETE /equipos/{id}` - Elimina un equipo y remueve a sus miembros asociados.

## 🎖️ Roles de Equipo (`/roles`)
* `POST /roles` - Agrega un rol interno (Capitán, Coach, etc.).
* `GET /roles` - Lista los roles internos disponibles en el sistema.
* `GET /roles/{id}` - Obtiene un rol específico por ID.
* `PUT /roles/{id}` - Actualiza el nombre de un rol.
* `DELETE /roles/{id}` - Elimina un rol interno.

## 👥 Miembros (`/miembros`)
* `POST /miembros` - Agrega un usuario a un equipo asignando un rol de equipo (valida que el usuario exista via Feign y no pertenezca a otra escuadra).
* `GET /miembros` - Obtiene la lista completa de miembros en todos los equipos.
* `GET /miembros/equipo/{idEquipo}` - Obtiene el roster o lista de jugadores de un equipo específico.
* `PUT /miembros/{id}` - Cambia el equipo o rol interno de un miembro.
* `DELETE /miembros/{id}` - Remueve a un jugador de su equipo.

---

# 🚀 Ejecución Manual

Para iniciar este microservicio en consola desde la raíz del proyecto, ejecuta:
```cmd
run-equipos.bat
```
> *Nota: Requiere que `postgres-db`, `kafka` y `ms-usuarios` estén levantados previamente.*

---

# 👨‍💻 Autor

* **Ignacio Alvarez** (Desarrollo y diseño de lógica de rosters, validaciones, mapeos, Swagger/OpenAPI, hypermedia HATEOAS, consumidores Kafka y cliente Feign).