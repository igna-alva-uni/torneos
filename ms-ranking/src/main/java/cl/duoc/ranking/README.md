# 👤 ms-ranking

![Java](https://img.shields.io/badge/Java-21+-orange.svg) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-brightgreen.svg) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg) ![OpenFeign](https://img.shields.io/badge/OpenFeign-Enabled-blue.svg)

> Microservicio encargado de la administración del ranking, tipos y registro de este dentro de la plataforma de torneos universitarios de E-Sports.

---

# 📖 Descripción

`ms-ranking` es el microservicio responsable de toda la información base relacionada con los rankings del sistema.

Administra:

* Rankings
* Tipos de ranking
* Registros del ranking
* modificaciones de estos

Este servicio actúa como una de las piezas claves del ecosistema, ya que es la bse de todo, donde el necesita información de otros microservicios para modificar los rankings, mediante comunicación REST usando OpenFeign.

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
    ranking.rankings
    ranking.tipos_ranking
    ranking.registros_ranking
```

---

# 🧩 Responsabilidades del Servicio

## rankings permite:

* Registrar rankings
* Consultar rankings
* Actualizar rankings
* Eliminar rankings

## tipo ranking permite:

* Registrar tipos de rankings
* Consultar tipo de rankings
* Actualizar tipos de rankings
* Eliminar tipos de rankings

## registro ranking permite:

* Ingresar registros 
* Consultar registros
* Eliminar registros

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

ms-ranking/
├── exception/
├── client/
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
run-ranking.bat
```

---

# ⚙️ Configuración Importante

Puerto

```text
9008
```

Schema PostgreSQL

```text
spring:
  jpa:
    properties:
      hibernate:
        default_schema: ranking
```

---

# 📌 Endpoints Principales

## Usuarios

```text
/api/v1/rankings/rankings
```

## Perfiles

```text
/api/v1/rankings/tipos
```

## Países

```text
/api/v1/rankings/registros
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
**Fabian Cornejo**
