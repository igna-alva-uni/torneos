# 🛡️ ms-equipos

![Java](https://img.shields.io/badge/Java-21+-orange.svg) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-brightgreen.svg) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg) ![OpenFeign](https://img.shields.io/badge/OpenFeign-Enabled-blue.svg)

> Microservicio encargado de la administración de equipos competitivos y sus integrantes.

---

# 📖 Descripción

`ms-equipos` administra toda la lógica relacionada con equipos competitivos dentro de la plataforma.

Permite:

* Crear equipos
* Gestionar miembros
* Asociar jugadores
* Administrar roles internos
* Mantener rosters competitivos

Este servicio se comunica con `ms-usuarios` mediante OpenFeign para validar usuarios existentes antes de agregarlos a equipos.

---

# 🛠️ Stack Tecnológico

## Backend

* Java 21
* Spring Boot 3.5.14
* Spring Data JPA
* Hibernate

## Arquitectura

* Microservicios
* Eureka Client
* OpenFeign

## Persistencia

* PostgreSQL
* Hibernate ORM

## Utilidades

* Lombok
* MapStruct
* Maven

---

# 🗄️ Esquema de Base de Datos

Este microservicio utiliza el esquema PostgreSQL:

```text
team
Tablas principales:
    team.equipos
    team.miembros_equipo
    team.roles_equipo
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

# 🔄 Comunicación Entre Servicios

Este servicio utiliza OpenFeign para comunicarse con:
``` text
ms-usuarios
```
Ejemplo:
``` text
@FeignClient(name = "ms-usuarios")
```
Validaciones implementadas:
``` texc
Existencia del usuario
Consulta de información pública
```

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

# 🚀 Ejecución

Desde la raíz del proyecto:
``` text 
run-equipos.bat
```
> para el correcto funcionamiento de este servicio primero se debe haber iniciado `ms-usuarios`

---

# ⚙️ Configuración Importante
Puerto
``` text
9005
```
Schema PostgreSQL
``` text
spring:
  jpa:
    properties:
      hibernate:
        default_schema: team
```

# 📌 Endpoints Principales
Equipos
``` text
/api/v1/equipos/equipos
```
Integrantes
``` text
/api/v1/equipos/integrantes
```

---

# 📚 Dependencias Importantes
* spring-boot-starter-web
* spring-boot-starter-data-jpa
* spring-cloud-starter-netflix-eureka-client
* spring-cloud-starter-openfeign
* postgresql
* lombok
* mapstruct

# 👨‍💻 Autor
Microservicio desarrollado por:

**Ignacio Alvarez**