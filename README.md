# 🏆 Gestor de Torneos Universitarios de E-Sports

![Java](https://img.shields.io/badge/Java-21+-orange.svg) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-brightgreen.svg) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg) ![Docker](https://img.shields.io/badge/Docker-Enabled-blue.svg) ![Maven](https://img.shields.io/badge/Maven-3.9+-red.svg) ![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-Enabled-red.svg)

> Plataforma distribuida basada en microservicios y arquitectura orientada a eventos para la gestión integral de torneos universitarios de E-Sports.

⚠️ **Importante:**
Actualmente el proyecto y los scripts `.bat` están diseñados y probados para ejecutarse **exclusivamente en entornos Windows**.

---

# 📖 Descripción General

Este proyecto consiste en una plataforma integral basada en una arquitectura de microservicios diseñada para administrar y gestionar Torneos Universitarios de E-Sports. El sistema abarca todo el ciclo de vida competitiva: desde el registro y autenticación de usuarios (con contraseñas hasheadas mediante BCrypt y tokens JWT), la creación de equipos y catálogo de juegos, hasta la gestión de torneos, programación de partidas, cálculo de estadísticas y generación de rankings.

La arquitectura distribuida utiliza **Spring Cloud Netflix Eureka** como registro de servicios y un **API Gateway** centralizado para el enrutamiento. Los servicios se comunican tanto de manera **síncrona (mediante OpenFeign)** como **asíncrona (mediante eventos publicados en Apache Kafka)** para garantizar la consistencia eventual entre los distintos esquemas de base de datos independientes implementados sobre un único contenedor PostgreSQL.

El sistema permite administrar:
* Usuarios y perfiles públicos (país, avatar, nickname).
* Autenticación, credenciales, bloqueos de cuenta y roles de usuario.
* Equipos competitivos, rosters y roles internos (Capitán, Titular, Suplente, Coach, Analista).
* Catálogo de videojuegos, plataformas y géneros.
* Torneos y sus formatos (eliminación directa, liga, etc.).
* Inscripciones de equipos a torneos.
* Partidas (fechas, rondas) y sus resultados.
* Rankings y puntuaciones por juego.
* Estadísticas y métricas de desempeño de jugadores y equipos.
* Notificaciones automáticas al usuario.

Cada microservicio posee:

* Responsabilidad única
* Base de datos lógica independiente mediante esquemas PostgreSQL
* Comunicación desacoplada
* Registro automático mediante Eureka Server

---

# 🛠️ Stack Tecnológico

## Backend & Frameworks
* **Java 21**
* **Spring Boot 3.5.14**
* **Spring Data JPA** & **Hibernate**
* **Spring Security** (Autenticación basada en JWT)

## Arquitectura de Microservicios
* **Spring Cloud Netflix Eureka** (Registro y descubrimiento de servicios)
* **Spring Cloud Gateway** (API Gateway en puerto 9000)
* **OpenFeign** (Comunicación REST síncrona entre servicios)

## Mensajería y Eventos
* **Apache Kafka** (Eventos asíncronos para sincronización de datos)
* **Kafka UI** (Panel de administración y visualización de tópicos)

## Persistencia
* **PostgreSQL 16** (Un único contenedor con esquemas independientes por servicio)

## Utilidades y Documentación
* **Lombok** & **MapStruct**
* **Springdoc OpenAPI (Swagger UI)** (Documentación interactiva de las APIs)
* **Maven** (Gestión de dependencias de proyectos multi-módulo)

## Infraestructura
* **Docker** & **Docker Compose**

---

# 🧩 Ecosistema de Microservicios

| Componente / Servicio | Puerto | Tipo | Responsabilidad / Descripción |
| :--- | :---: | :---: | :--- |
| **🌐 Eureka Server** | 8761 | Infraestructura | Registro y descubrimiento dinámico de microservicios. |
| **⚙️ API Gateway** | 9000 | Infraestructura | Enrutamiento dinámico, reescritura de paths, agregación de Swagger UI y control de CORS. |
| **👤 ms-usuarios** | 9001 | Negocio | Gestión de usuarios, perfiles públicos (nickname, avatar) y catálogo de países. |
| **🔒 ms-autenticaciones** | 9002 | Negocio | Seguridad lógica: credenciales, roles, hasheo BCrypt y generación de tokens JWT. |
| **🎮 ms-juegos** | 9003 | Negocio | Catálogo de videojuegos, géneros y plataformas compatibles. |
| **🏆 ms-torneos** | 9004 | Negocio | Creación y administración de torneos y formatos de competencia. |
| **🛡️ ms-equipos** | 9005 | Negocio | Creación de equipos, asignación de roles internos y gestión de miembros. |
| **⚔️ ms-partidas** | 9006 | Negocio | Programación de partidas, llaves de torneo y resultados. |
| **📝 ms-inscripciones** | 9007 | Negocio | Proceso de registro y estado de inscripción de equipos en torneos. |
| **📈 ms-ranking** | 9008 | Negocio | Tabla de posiciones y cálculo de puntos por juego. |
| **🔔 ms-notificaciones** | 9009 | Soporte | Envío de notificaciones y alertas de sistema a los usuarios. |
| **📊 ms-estadisticas** | 9010 | Negocio | Métricas de rendimiento, historiales de victorias/derrotas de jugadores y equipos. |

---

# 🗄️ Arquitectura de Base de Datos

El sistema utiliza **1 único contenedor PostgreSQL** que aloja la base de datos centralizada `torneos`, subdividida en esquemas lógicos independientes para garantizar que cada microservicio maneje su propio dominio de datos de forma aislada:

```text
torneos (Database)
│   (esquemas:)
├── auth          --> ms-autenticaciones (credenciales, roles, relaciones)
├── team          --> ms-equipos (equipos, miembros, roles internos)
├── stats         --> ms-estadisticas (estadísticas de juego)
├── registration  --> ms-inscripciones (inscripciones a torneos)
├── game          --> ms-juegos (juegos, plataformas, géneros)
├── notification  --> ms-notificaciones (alertas y notificaciones)
├── match         --> ms-partidas (partidas, resultados)
├── rankings      --> ms-ranking (tablas de puntajes y posiciones)
├── tournaments   --> ms-torneos (torneos, premios, formatos)
└── users         --> ms-usuarios (usuarios, perfiles, países)
```

---

# 🛠️ Requisitos Previos

Asegúrate de contar con las siguientes herramientas configuradas en tu sistema Windows y añadidas a tus variables de entorno (`PATH`):

1. **Java Development Kit (JDK) 21+**
   * Descarga: [Oracle JDK 21](https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.exe)
   * Verificación en consola: java -version
2. **Apache Maven 3.9+**
   * Descarga: [Maven Download](https://maven.apache.org/download.cgi#CurrentMaven)
   * instalacion: [Maven Installation proces](https://maven.apache.org/install.html)
   * Verificación en consola: mvn -version
3. **Docker Desktop** (Esencial para inicializar PostgreSQL, Kafka y Eureka)
   * Descarga: [Docker Desktop](https://www.docker.com/products/docker-desktop/)
   * Verificación en consola: docker --version y docker-compose --version
4. **Git**
   * Descarga: [Git](https://git-scm.com/install/windows)
   * Verificación en consola: git --version

---

# 🚀 Instalación y Despliegue

## 1. Clonar el repositorio
```cmd
git clone https://github.com/igna-alva-uni/torneos.git
cd torneos
```

---

## 2. 🐳 Inicialización de la Infraestructura (Docker)

El proyecto cuenta con un script de inicio rápido en la raíz que se encarga de apagar contenedores viejos, limpiar volúmenes (evitando conflictos de tópicos Kafka y tablas PostgreSQL) y levantar el stack básico en segundo plano:

```cmd
run-dockers.bat
```

Este comando iniciará:
1. **Contenedor PostgreSQL** (`postgres-db`) expuesto en el puerto local **5433**.
   * Nota: Al iniciar por primera vez, ejecutará automáticamente los scripts SQL ubicados en `init-multi-db/*.sql` para construir y poblar todos los esquemas lógicos.
2. **Broker Kafka** (`kafka_broker`) expuesto internamente en la red de Docker.
3. **Kafka UI** en el puerto **8080** (`http://localhost:8080`).

---

## 3. 🔨 Compilación de los Microservicios (Maven Reactor)

Para compilar y descargar las dependencias de todos los microservicios en el orden correcto y sin conflictos, ejecuta el siguiente script desde la raíz del proyecto:

```cmd
install.bat
```
*(Este script limpia la caché de Maven `.m2`, borra las carpetas `target` residuales y realiza un `mvn clean install` forzando la actualización de dependencias)*.

---

## 4. ▶️ Ejecución del Proyecto

### Opción A — Ejecutar en Modo Local / Desarrollo (Recomendado para Debugging)

Desde la raíz, ejecuta el script de arranque múltiple:
```cmd
run-all.bat
```
Este script levantará de forma secuencial Eureka Server, todos los microservicios de negocio y el API Gateway en ventanas separadas del símbolo del sistema.

### Opción B — Ejecutar Completamente en Docker
Si prefieres que todo corra dentro de Docker, simplemente ejecuta:
```cmd
docker compose up -d
```
*(Construirá y levantará imágenes locales para cada microservicio usando sus respectivos `dockerfile`)*.

---

# 🌐 Sitios de Interés, Swagger y Autenticación

Una vez que el sistema esté en ejecución, puedes acceder a las siguientes URLs para interactuar con la plataforma y probar los flujos principales:

### 📊 Paneles de Monitoreo e Infraestructura
* **Eureka Server Dashboard:** [http://localhost:8761](http://localhost:8761) (Verifica el registro dinámico de todos los microservicios).
* **Kafka UI:** [http://localhost:8080](http://localhost:8080) (Administración de tópicos y monitoreo de mensajes de eventos).

### 📖 Documentación de APIs (Swagger / OpenAPI)
* **Swagger UI Agregado (API Gateway):** [http://localhost:9000/ms/swagger](http://localhost:9000/ms/swagger) (Un panel centralizado que agrega y expone de forma interactiva la documentación de todos los microservicios).
* **Swagger UI individuales (vía Gateway):**
  * `ms-usuarios`: [http://localhost:9000/usuarios/swager](http://localhost:9000/usuarios/swager)
  * `ms-autenticaciones`: [http://localhost:9000/autenticaciones/swager](http://localhost:9000/autenticaciones/swager)
  * `ms-equipos`: [http://localhost:9000/equipos/swager](http://localhost:9000/equipos/swager)
  * `ms-juegos`: [http://localhost:9000/juegos/swager](http://localhost:9000/juegos/swager)
  * `ms-torneos`: [http://localhost:9000/torneos/swager](http://localhost:9000/torneos/swager)
  * `ms-partidas`: [http://localhost:9000/partidas/swager](http://localhost:9000/partidas/swager)
  * `ms-inscripciones`: [http://localhost:9000/inscripciones/swager](http://localhost:9000/inscripciones/swager)
  * `ms-ranking`: [http://localhost:9000/ranking/swager](http://localhost:9000/ranking/swager)
  * `ms-notificaciones`: [http://localhost:9000/notificaciones/swager](http://localhost:9000/notificaciones/swager)
  * `ms-estadisticas`: [http://localhost:9000/estadisticas/swager](http://localhost:9000/estadisticas/swager)

### 🔐 Endpoints de Autenticación (A través del API Gateway)
El Gateway (`puerto 9000`) cuenta con reescritura de rutas para simplificar el acceso a los endpoints de seguridad (`/api/v1/auth/**` es mapeado automáticamente a `ms-autenticaciones`):

* **Registro de Usuario (Unificado):**
  * **Endpoint:** `POST http://localhost:9000/api/v1/auth/registro` (o `POST http://localhost:9000/api/v1/autenticaciones/registro`)
  * **Descripción:** Crea la entidad pública en `ms-usuarios` e introduce las credenciales hasheadas en `ms-autenticaciones` con rol `ROLE_PLAYER` por defecto. Devuelve un JWT token firmado.
* **Inicio de Sesión (Login):**
  * **Endpoint:** `POST http://localhost:9000/api/v1/auth/login` (o `POST http://localhost:9000/api/v1/autenticaciones/login`)
  * **Descripción:** Valida las credenciales contra PostgreSQL y genera un token JWT con la información de roles e identificador del usuario.
* **Cierre de Sesión (Logout):**
  * **Descripción:** Al ser un modelo de autenticación **sin estado (stateless)** por medio de JWT, el servidor no mantiene sesiones persistentes en memoria. El **Logout** se realiza del lado del cliente (frontend/Postman) destruyendo o eliminando el token guardado en el almacenamiento local (`LocalStorage` / `SessionStorage`) y omitiendo la cabecera `Authorization: Bearer <token>` de las peticiones subsiguientes. No requiere de un endpoint en la API para ser ejecutado.

---

# 🔄 Comunicación Entre Servicios

El proyecto combina dos patrones de diseño para la comunicación interna:
1. **Comunicación Síncrona (REST via OpenFeign):** Utilizada cuando un servicio necesita respuestas en tiempo real. Por ejemplo, `ms-autenticaciones` y `ms-equipos` llaman a `ms-usuarios` para validar la existencia y obtener datos públicos del usuario.
2. **Comunicación Asíncrona (Eventos via Apache Kafka):** Utilizada para propagar cambios y mantener consistencia eventual sin acoplar los servicios. Cuando un usuario es creado, modificado o eliminado en `ms-usuarios`, se publican eventos en los tópicos correspondientes (`usuarios-creados`, `usuarios-actualizados`, `usuarios-eliminados`), los cuales son consumidos por `ms-autenticaciones` y `ms-equipos` para actualizar localmente sus tablas.

---

# 📌 Consideraciones Técnicas

* **MapStruct + Lombok:** Integrados mediante configuración de compilador en el `pom.xml` para generar automáticamente los mapeos entre entidades JPA y DTOs, evitando código repetitivo.
* **HATEOAS:** Las respuestas REST im
portantes incluyen enlaces estructurados en el JSON (Hypermedia As The Engine Of Application State) facilitando la navegación en la API.
* **Hasheo de Contraseñas:** Se utiliza BCrypt para encriptar contraseñas. Existe compatibilidad hacia atrás para contraseñas de datos mockeados en base de datos que comiencen con el prefijo `HASH_`.

---

## 📁 Entregables de Evaluación (Links Rápidos)

Para facilitar la revisión del proyecto según la rúbrica, aquí se encuentran los documentos clave:

* 📄 **Documento de Arquitectura y Contrato de APIs:** [`DOCUMENTO DE ARQUITECTURA Y CONTRATO DE MICROSERVICIOS.docx`](./DOCUMENTO%20DE%20ARQUITECTURA%20Y%20CONTRATO%20DE%20MICROSERVICIOS.docx)
* 🧪 **Colección de Postman:** [`torneos.postman_collection.json`](./torneos.postman_collection.json) *(Importar directamente en Postman para probar el sistema completo a través del API Gateway)*.
* 🗺️ **Roadmap del Proyecto:** [`RODEMAP TORNEOS.docx`](./RODEMAP%20TORNEOS.docx)

---

## 👥 Equipo de Desarrollo

Este ecosistema de microservicios fue desarrollado colaborativamente. Responsabilidades de implementación específica en esta entrega:

* <a href="https://github.com/igna-alva-uni"><img src="https://images.weserv.nl/?url=github.com/igna-alva-uni.png&mask=circle&w=80" width="35" align="middle"></a> **Ignacio Alvarez** - `ms-usuarios`, `ms-autenticaciones`, `ms-equipos`, `README.md` principal y de los ms responsables, estructuración de base de datos e infraestructura Docker, swagger ui para todos los ms, pruebas unitarias de todos los ms, spring security y API Gateway.
* <a href="https://github.com/fabcornejom"><img src="https://images.weserv.nl/?url=github.com/fabcornejom.png&mask=circle&w=80" width="35" align="middle"></a> **Fabian Cornejo** - `ms-estadisticas`, `ms-ranking`, apoyo en el desarollo de las pruebas unitarias de todos los ms y swagger ui.
* <a href="https://github.com/esmartineza-spec"><img src="https://images.weserv.nl/?url=github.com/esmartineza-spec.png&mask=circle&w=80" width="35" align="middle"></a> **Esteban Martinez** - `ms-juegos`, `ms-torneos` y `ms-partidas`
* <a href="https://github.com/autistabakanconmetralleta"><img src="https://images.weserv.nl/?url=github.com/autistabakanconmetralleta.png&mask=circle&w=80" width="35" align="middle"></a> **Luciano Diaz** - `ms-inscripciones` y `ms-notificaciones`

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
