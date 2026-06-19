API Gateway + Service
Discovery con Eureka

DESARROLLO FULLSTACK I

Programación de la asignatura ¿Dónde vamos?

Unidad 1 (completada)

Microservicios y Backend
HTTP y REST
GitHub
Spring Boot: Arquitectura MVC y Patrón CSR
Múltiples endpoints y Operaciones CRUD
Buenas Prácticas, ResponseEntity y Validaciones
Manejo de excepciones

Unidad 2 (completada)

Base de Datos y ORM
Microservicios en Arquitecturas Distribuidas
Comunicación sincrónica con Feign Clients
Comunicación asincrónica con Kafka
Buenas prácticas con ResponseEntity y Validaciones

? Unidad 3 (en curso)

Seguridad y JJWT
Documentación con Swagger
Implementación de HATEOAS en la Documentación de APIs

? API Gateway
? Pruebas unitarias, análisis de tests y generación de datos con DataFaker
? Despliegue local y remoto de microservicios

• Contenidos

•

Introducción a YAML

• Rol del API Gateway en Microservicios

• Eureka

• Desarrollo de guía práctica.

• Introducción a YAML

YAML (YAML Ain't Markup Language) es un formato de serialización de datos diseñado para ser muy fácil de leer

por humanos. Se usa principalmente en microservicios como Kubernetes debido a su claridad para manejar

estructuras complejas. Su sintaxis emplea sangrías y pares clave-valor, eliminando por completo el uso de llaves o

corchetes.

Características clave

• Legibilidad: Está diseñado para ser fácil de leer y escribir por humanos, con una estructura limpia.

• Serialización de datos: Permite representar estructuras de datos complejas en un formato simple y

estandarizado.

• Sintaxis: Utiliza sangría para definir la estructura, similar a Python, en lugar de etiquetas o corchetes.

• Comentarios: Se pueden añadir comentarios con el símbolo de almohadilla (#).

• Extensiones de archivo: Los archivos suelen tener la extensión .yml o .yaml.

• Sintaxis Básica de YAML

La sintaxis de YAML se basa en tres estructuras principales:

A. Mapeos (Objetos / Diccionarios): Se utilizan para representar estructuras jerárquicas (llave-valor).

La indentación con espacios (nunca tabulaciones) define la anidación.

Properties

server.port=8080

YAML

server:

spring.application.name=gateway

port: 8080

spring:

application:

name: gateway

• Sintaxis Básica de YAML

B. Secuencias (Arrays / Listas): Se utilizan

C. Tipos de Datos: YAML maneja

para representar listas ordenadas de

elementos. Se indican con un guion (-)

seguido de un espacio.

automáticamente tipos de datos básicos

(cadenas, números, booleanos). Las

cadenas a menudo no necesitan comillas.

Properties

my.list[0]=item1

YAML

my:

my.list[1]=item2

list:

- item1

- item2

Tipo

YAML

String (Cadena)

name: My Service

Number (Número)

port: 8080

Boolean (Booleano)

enabled: true

• ¿Qué es un API Gateway?

Una API Gateway es un servidor que actúa como puerta de entrada única para redirigir, asegurar y administrar peticiones de

clientes hacia microservicios.

Tiene las siguientes características:

• Punto único de entrada para todos los clientes (web, móvil, Postman).

• Orquestador de rutas hacia los microservicios internos.

• Capa de seguridad, aplica validaciones, autenticación o control de tráfico.

• Capa de filtros transversales, como logs, manejo de cabeceras, limitar peticiones (rate-limiting) o intercambio de

recursos de origen cruzado (CORS).

• Concentra todo en una única URL para no exponer los microservicios al cliente.

• Ventajas de YAML en Spring Cloud Gateway

En el contexto de la configuración distribuida y el API Gateway, YAML ofrece grandes beneficios:

Claridad de Rutas: La definición de rutas, predicados y filtros en Spring Cloud Gateway es una estructura

de lista anidada. YAML maneja esto de forma natural, haciendo que el archivo de configuración sea mucho

más fácil de leer y mantener que el formato properties.

• Ventajas de YAML en Spring Cloud Gateway

Perfiles  Simplificados:  Al  definir  configuraciones  específicas  para  entornos  (dev,  prod,  test),  YAML

permite  usar  el  símbolo  ---  para  separar  las  configuraciones  de  los  perfiles  en  un  único  archivo

application.yml.

• ¿Qué es Eureka?

Eureka es un Service Registry y Service Discovery desarrollado por

Netflix y adoptado por Spring Cloud.

Su  función  es  permitir  que  los  microservicios  se  descubran  entre  sí

dinámicamente, sin necesidad de codificar URLs o puertos en el código

fuente.

Eureka implementa el patrón “Client-Side Service Discovery”, donde:

EUREKA

• Cada microservicio se registra en Eureka.

• Eureka mantiene una lista de servicios disponibles.

• Los clientes (como Gateway, webapps o servicios internos) consultan

a Eureka para resolver el service-id en tiempo real.

• El  cliente  decide  a  qué  instancia  conectarse,  permitiendo  balancear

carga de forma nativa.

• Service Discovery con Eureka

Eureka (Spring Cloud Netflix Eureka) es la implementación estándar del patrón

Service Discovery. Resuelve el problema de las rutas fijas.

En una arquitectura distribuida, los microservicios:

• Pueden iniciarse en puertos aleatorios.

• Pueden escalarse (tener múltiples instancias en diferentes IPs).

• Pueden fallar y ser reemplazados.

Eureka proporciona:

• Service  Registry

(Servidor):  Un  servidor  central  donde

todos

los

microservicios se registran al iniciarse.

• Service Client (Cliente): La dependencia que cada microservicio y el Gateway

usan para registrarse o consultar la ubicación de otros.

• Cierre de esta presentación

• ¿Qué es un archivo YAML?

• ¿Cuál es el rol del API Gateway en la tecnología de microservicios?

• ¿Qué es Eureka y para qué sirve?

• ACTIVIDAD PRÁCTICA

1. Revisemos como Incorporar API

Gateway a la Versión Actual de

Biblioteca (Apoyo mediante Video)

2. Usa el Proyecto de Biblioteca con

API Gateway como patrón de

desarrollo y aplícalo a tu proyecto

semestral


