# 🔒 ms-autenticaciones

![Java](https://img.shields.io/badge/Java-21+-orange.svg) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-brightgreen.svg) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg) ![OpenFeign](https://img.shields.io/badge/OpenFeign-Enabled-blue.svg)

> Microservicio encargado de la autenticación, credenciales y administración de roles del sistema.

---

# 📖 Descripción

`ms-autenticaciones` administra la seguridad lógica de los usuarios registrados en la plataforma.

Este servicio maneja:
* Credenciales
* Emails
* Contraseñas hasheadas
* Roles
* Bloqueos de cuentas

Además, valida la existencia de usuarios utilizando comunicación REST con `ms-usuarios` mediante OpenFeign.

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

``` text
auth
Tablas principales:
    auth.auth_usuarios
    auth.roles
    auth.roles_usuarios
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

# 🔄 Comunicación Entre Servicios

Este servicio utiliza OpenFeign para comunicarse con:
``` text
ms-usuarios
```
Ejemplo:
``` text
@FeignClient(name = "ms-usuarios")
``` 
Validación implementada:
Verificar existencia del usuario antes de registrar credenciales

---

# 🔐 Modelo de Seguridad Actual

## Actualmente el proyecto utiliza:
* la simulacion de Hash de contraseñas para la version actual
* Roles persistidos en PostgreSQL
* Relaciones ManyToMany entre usuarios y roles

---

# 📂 Estructura Principal
``` text
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

# 🚀 Ejecución
Desde la raíz del proyecto:
``` text
run-autenticaciones.bat
```
> para el correcto funcionamiento de este servicio primero se debe haber iniciado `ms-usuarios`


---

# ⚙️ Configuración Importante
Puerto:
``` text
9002
```
Schema PostgreSQL
``` text
spring:
  jpa:
    properties:
      hibernate:
        default_schema: auth
```

---

# 📌 Endpoints Principales
Credenciales
``` text
/api/v1/autenticaciones/auth
```
Roles
``` text
/api/v1/autenticaciones/roles
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