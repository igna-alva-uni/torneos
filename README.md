# 🏆 Gestor de Torneos Universitarios de E-Sports

![Java](https://img.shields.io/badge/Java-21+-orange.svg) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-brightgreen.svg) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg) ![Docker](https://img.shields.io/badge/Docker-Enabled-blue.svg) ![Maven](https://img.shields.io/badge/Maven-3.9+-red.svg)

> Plataforma distribuida basada en microservicios para la gestión de torneos universitarios de E-Sports.

⚠️ **Importante:**
Actualmente el proyecto y los scripts `.bat` fueron diseñados para ejecutarse en **exclusivamente en entornos Windows**.

---

# 📖 Descripción General

Este proyecto consiste en una plataforma integral basada en arquitectura de microservicios diseñada para administrar y gestionar Torneos Universitarios de E-Sports. El sistema abarca todo el ciclo de vida de un entorno competitivo: desde el registro y autenticación de usuarios, la creación de equipos y catálogo de juegos, hasta la gestión de torneos, programación de partidas, cálculo de estadísticas y generación de rankings. La arquitectura distribuida permite que cada componente escale de forma independiente, utilizando sus propias bases de datos PostgreSQL y comunicándose a través de un ecosistema gestionado por Spring Cloud Netflix Eureka.

El sistema permite administrar:

* Usuarios y perfiles
* Autenticación y roles
* Equipos competitivos
* Juegos disponibles
* Torneos
* Inscripciones
* Partidas
* Rankings
* Estadísticas
* Notificaciones

Cada microservicio posee:

* Responsabilidad única
* Base de datos lógica independiente mediante esquemas PostgreSQL
* Comunicación desacoplada
* Registro automático mediante Eureka Server

---

# 🛠️ Stack Tecnológico

## Backend

* Java 21
* Spring Boot 3.5.14
* Spring Data JPA
* Hibernate

## Arquitectura

* Microservicios
* Spring Cloud Netflix Eureka
* OpenFeign

## Persistencia

* PostgreSQL
* Hibernate ORM

## Utilidades

* Lombok
* MapStruct
* Maven

## Infraestructura

* Docker
* Docker Compose

---

# 🧩 Ecosistema de Microservicios

| Microservicio         | Puerto | Responsabilidad                          |
| --------------------- | ------ | ---------------------------------------- |
| 🌐 Eureka Server      | 8761   | Registro y descubrimiento de servicios   |
| 👤 ms-usuarios        | 9001   | Gestión de perfiles, países y usuarios |
| 🔒 ms-autenticaciones | 9002   | Seguridad, credenciales y roles          |
| 🎮 ms-juegos          | 9003   | Catálogo de videojuegos                 |
| 🏆 ms-torneos         | 9004   | Gestión de torneos                      |
| 🛡️ ms-equipos       | 9005   | Equipos y rosters                        |
| ⚔️ ms-partidas      | 9006   | Partidas y resultados                    |
| 📝 ms-inscripciones   | 9007   | Registro a torneos                       |
| 📈 ms-ranking         | 9008   | Rankings y puntuaciones                  |
| 🔔 ms-notificaciones  | 9009   | Sistema de notificaciones                |
| 📊 ms-estadisticas    | 9010   | Estadísticas y métricas                |

---

# 🗄️ Arquitectura de Base de Datos

El proyecto utiliza:

* **1 contenedor PostgreSQL**
* **Múltiples esquemas independientes**

mapa:

```text
torneo (db)
│   (esqemas:)
├── auth
├── team
├── stats
├── registration
├── game
├── notification
├── match
├── rankings
├── tournaments
└── user
```

Cada microservicio trabaja sobre su propio esquema para mantener separación lógica y modularidad.

---

# 🛠️ Requisitos Previos

Antes de clonar e iniciar el proyecto, asegúrate de tener instaladas las siguientes herramientas en tu entorno Windows y configuradas en tus variables de entorno (PATH):

1. **Java Development Kit (JDK) 21+** * Descarga: [Oracle JDK 21](https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.exe)
   * Verificación en consola: java -version
2. **Apache Maven 3.9+** * Descarga: [Maven Download](https://maven.apache.org/download.cgi#CurrentMaven)
   * instalacion: [Maven Installation proces](https://maven.apache.org/install.html)
   * Verificación en consola: mvn -version
3. **Docker Desktop** (Para inicializar las bases de datos PostgreSQL)
   * Descarga: [Docker Desktop](https://www.docker.com/products/docker-desktop/)
   * Verificación en consola: docker --version y docker-compose --version
4. **Git**
   * Descarga: [Git](https://git-scm.com/install/windows)
   * Verificación en consola: git --version

---

# 🚀 Instalación

## 1. Clonar el repositorio

```cmd
git clone https://github.com/igna-alva-uni/torneos.git
cd torneos
```

---

## 2. 🐳 Inicialización de PostgreSQL

Entrar a la carpeta de scripts:

```cmd
cd init-multi-db
```

### Crear contenedor PostgreSQL

```cmd
docker_install_torneos.bat
```

### Crear esquemas, tablas y datos iniciales

```cmd
docker_compile_dbs.bat
```

Luego volver al directorio raíz:

```cmd
cd ..
```

---

## 3. 🔨 Compilación del Proyecto

Desde la raíz:

```cmd
compile.bat
```

Este script:

* Descarga dependencias Maven
* Compila todos los microservicios
* Genera clases MapStruct
* Verifica errores de compilación

---

## 4. ▶️ Ejecución del Proyecto

### Opción 1 — Ejecutar Todo Automáticamente (Recomendado)

```cmd
run-all.bat
```

Esto iniciará:

1. Eureka Server
2. Todos los microservicios

---

### Opción 2 — Ejecutar Manualmente

#### Iniciar Eureka

```cmd
run-eureka.bat
```

Esperar aproximadamente 15 segundos.

---

#### Iniciar microservicios individuales

Ejemplos:

```cmd
run-usuarios.bat
run-autenticaciones.bat
run-equipos.bat
```

---

## 🌐 Eureka Dashboard

Una vez iniciado Eureka:

```text
http://localhost:8761
```

Desde ahí podrás visualizar todos los microservicios registrados.

---

# 📂 Estructura del Proyecto

```text
/
├── eureka/
├── init-multi-db/
├── ms-autenticaciones/
├── ms-equipos/
├── ms-estadisticas/
├── ms-inscripciones/
├── ms-juegos/
├── ms-notificaciones/
├── ms-partidas/
├── ms-ranking/
├── ms-torneos/
├── ms-usuarios/
│
├── compile.bat
├── install.bat
├── run-all.bat
├── run-*.bat
│
└── README.md
```

---

# 🔄 Comunicación Entre Servicios

El proyecto soporta:

## Comunicación síncrona

Mediante:

* OpenFeign

## Comunicación asíncrona *(en desarrollo)*

Mediante:

* Apache Kafka

---

# 🧪 Testing y Desarrollo

Herramientas recomendadas:

* VSCode
* Postman
* Docker Desktop
* IntelliJ IDEA
* DBeaver

---

# 📌 Consideraciones Técnicas

## MapStruct + Lombok

El proyecto utiliza:

* `mapstruct`
* `lombok`
* `lombok-mapstruct-binding`

para generación automática de DTOs y mappers.

---

## PostgreSQL Schemas

Cada microservicio debe configurar su esquema:

Ejemplo:

```yaml
spring:
  jpa:
    properties:
      hibernate:
        default_schema: users
```

---

## Java Version

El proyecto está configurado para:

```text
Java 21
```

Aunque algunas máquinas podrían ejecutarlo con versiones superiores, no está oficialmente garantizado.

---

# 📚 Documentación Adicional

En la raíz del proyecto se incluyen:

* Documentación de arquitectura
* Contratos de microservicios
* Roadmap del proyecto
* Scripts de automatización

---

---

## 📁 Entregables de Evaluación (Links Rápidos)

Para facilitar la revisión del proyecto según la rúbrica, aquí se encuentran los documentos clave:

* 📄 **Documento de Arquitectura y Modelo C4:** [`DOCUMENTO DE ARQUITECTURA Y CONTRATO DE MICROSERVICIOS.docx`](./tree/main/)
* 🧪 **Colección de Postman:** [`Torneos_Postman_Collection.json`](./tree/main/Torneos_Postman_Collection.json) *(Importar directamente en Postman para probar los endpoints)*.
* 🗺️ **Roadmap del Proyecto:** [`RODEMAP TORNEOS.docx`](./tree/main/RODEMAP%20TORNEOS.docx)

---

## 👥 Equipo de Desarrollo

Este ecosistema fue desarrollado en conjunto. Mis responsabilidades específicas en la implementación de esta entrega fueron los siguientes microservicios:

* **[ ignacio alvarez ]** - `ms-usuarios`, `ms-autenticaciones`, `ms-equipos`.
* **[ Nombre Compañero 1 ]** - `[Microservicios a su cargo]`
* **[ Nombre Compañero 2 ]** - `[Microservicios a su cargo]`
* **[ Nombre Compañero 3 ]** - `[Microservicios a su cargo]`

Proyecto académico orientado al aprendizaje de:

* Arquitectura de microservicios
* Sistemas distribuidos
* Spring Cloud
* Comunicación entre servicios
* PostgreSQL
* Docker
* Diseño backend empresarial

---

# 📄 Licencia

Proyecto desarrollado con fines educativos y académicos.
