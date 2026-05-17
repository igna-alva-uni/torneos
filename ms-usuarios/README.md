# 👤 ms-usuarios

![Java](https://img.shields.io/badge/Java-21+-orange.svg) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-brightgreen.svg) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg) ![OpenFeign](https://img.shields.io/badge/OpenFeign-Enabled-blue.svg)

> Microservicio encargado de la administración de usuarios, perfiles y países dentro de la plataforma de torneos universitarios de E-Sports.

---

# 📖 Descripción

`ms-usuarios` es el microservicio responsable de toda la información base relacionada con los usuarios del sistema.

Administra:

* Usuarios
* Perfiles públicos
* Países
* Nicknames
* Avatares

Este servicio actúa como una de las piezas centrales del ecosistema, ya que otros microservicios dependen de él para validar información de usuarios mediante comunicación REST usando OpenFeign.

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
users
Tablas principales:
    users.usuarios
    users.perfiles
    users.paises
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

# 🔄 Comunicación con Otros Servicios

Este microservicio:

Se registra automáticamente en Eureka
Puede ser consumido mediante OpenFeign
Expone endpoints REST para validaciones externas

Ejemplo:

```text
@FeignClient(name = "ms-usuarios")
```

---

# 📂 Estructura Principal

ms-usuarios/
├── controller/
├── service/
├── repository/
├── mapper/
├── model/
├── dtos/
├── config/
└── resources/

---

# 🚀 Ejecución

Desde la raíz del proyecto:

```text
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

# 📌 Endpoints Principales

## Usuarios

```text
/api/v1/usuarios/usuarios
```

## Perfiles

```text
/api/v1/usuarios/perfiles
```

## Países

```text
/api/v1/usuarios/paises
```

# 🧪 Tecnologías Utilizadas Internamente

## MapStruct utilizado para:

* Conversión DTO ↔ Entity
* Reducción de boilerplate

## Lombok utilizado para:

* Getters/Setters automáticos
* Constructores
* Simplificación de entidades

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
