Implementación de
HATEOAS en la
Documentación de APIs
DESARROLLO FULLSTACK I

Programación de la asignatura ¿Dónde vamos?
Unidad 1 (completada)
Microservicios y Backend
HTTP y REST
GitHub
Spring Boot: Arquitectura MVC y Patrón CSR
Múltiples endpointsy Operaciones CRUD
Buenas Prácticas, ResponseEntityy Validaciones
Manejo de excepciones
Unidad 2 (completada)
Base de Datos y ORM
Microservicios en Arquitecturas Distribuidas
Comunicación sincrónica con FeignClients
Comunicación asincrónica con Kafka
Buenas prácticas con ResponseEntityy Validaciones
? Unidad 3 (en curso)
Seguridad y JJWT
Documentación con Swagger
? Implementación de HATEOAS en la Documentación de APIs
? Pruebas unitarias, análisis de testsy generación de datos con DataFaker
? API Gateway
? Despliegue local y remoto de microservicios

• Contenidos
• ¿Qué es HATEOAS?.
• Funcionamiento.
• Beneficios.
• Desarrollo de guía práctica.

| •   | ¿Qué es HATEOAS? |     |     |     |
| --- | ---------------- | --- | --- | --- |
{
| HATEOAS:  | Hypermedia | As The  |  "_embedded": { |     |
| --------- | ---------- | ------- | --------------- | --- |
   "libroResponseList": [
     {
| Engine | Of Application | State. |     |     |
| ------ | -------------- | ------ | --- | --- |
       "id": 1,
       "isbn": "9798344055985",
       "titulo": "Moby Dick",
       "editorial": "Elderwand",
| Es un componente clave del estilo de  |     |     |        "anioPublicacion": 2024, |     |
| ------------------------------------- | --- | --- | ------------------------------- | --- |
       "autor": "Herman Melville",
|     |     |     |       |  "categorias": [ |
| --- | --- | --- | ----- | ---------------- |
arquitectura REST.
|                           |     |     |       |    {                      |
| ------------------------- | --- | --- | ----- | ------------------------- |
|                           |     |     |       |      "id": 4,             |
|                           |     |     |       |      "nombre": "Aventura" |
|                           |     |     |       |    },                     |
| Proporciona una forma de  |     |     |       |    {                      |
|                           |     |     |       |      "id": 5,             |
|                           |     |     |       |      "nombre": "Clásico"  |
navegación dinámica y auto-
|             |                             |     |       |    }                                                  |
| ----------- | --------------------------- | --- | ----- | ----------------------------------------------------- |
|             |                             |     |       |  ],                                                   |
| descriptiva | en una API RESTful.         |     |       |                                                       |
|             |                             |     |       |  "_links": {                                          |
|             |                             |     |       |    "self": {                                          |
|             |                             |     |       |      "href": "http://localhost:9002/api/v1/libros/1"  |
|             |                             |     |       |    },                                                 |
|             |                             |     |       |    "update": {                                        |
| Permite     | a los clientes descubrir y  |     |       |                                                       |
|             |                             |     |       |      "href": "http://localhost:9002/api/v1/libros/1", |
|             |                             |     |       |      "title": "PUT - Actualizar libro"                |
manipular recursos de manera
|     |     |     |       |    },...                  |
| --- | --- | --- | ----- | ------------------------- |
|     |     |     |       |    "agregar-categoria": { |
autónoma a través de enlaces             "href": "http://localhost:9002/api/v1/libros/libro/1/categoria/{categoriaId}",
|     |     |     |       |      "title": "POST - Asociar categoría al libro", |
| --- | --- | --- | ----- | -------------------------------------------------- |
|     |     |     |       |      "templated": true                             |
hipermedia.
|     |     |     |       |    },... |
| --- | --- | --- | ----- | -------- |

• Funcionamiento
En una API RESTful habilitada con HATEOAS, cada respuesta a una solicitud de cliente no solo
contiene la representación del recurso solicitado, sino también enlaces (links) a otras acciones
disponibles relacionadas con ese recurso. Esto permite que el cliente navegue por la API siguiendo estos
enlaces, sin necesidad de conocer de antemano la estructura y las operaciones de la API.
{
"_embedded": {
"libroResponseList": [
{
"id": 1,
"isbn": "9798344055985",
Data content
"titulo": "Moby Dick",...
"_links": {
"self": {
"href": "http://localhost:9002/api/v1/libros/1"
},
"update": {
Hypermedia "href": "http://localhost:9002/api/v1/libros/1",
"title": "PUT - Actualizar libro"
},...
"agregar-categoria": {
"href": "http://localhost:9002/api/v1/libros/libro/1/categoria/{categoriaId}",
"title": "POST - Asociar categoría al libro",
"templated": true
},...

Beneficios
• Hypermedia
Es cualquier tipo de contenido que contiene enlaces a
otros recursos. En el contexto de HATEOAS, los enlaces
proporcionan información sobre las posibles acciones que
pueden realizarse en un recurso.
• RESTful API
Una API que sigue los principios del estilo de arquitectura
REST, que incluye el uso de hipermedios para la
navegación.

• Cierre de esta presentación
• ¿Qué es HATEOAS?.
• ¿Cómo funciona HATEOAS?
• ¿Qué beneficios tiene HATEOAS?

• Programa Fuente de Ejemplo de Biblioteca
1. Revisemos como Incorporar
HATEOAS a la Versión Actual de
Biblioteca (Apoyo mediante Video)
2. Usa el Proyecto de Biblioteca con
HATEOAS como patrón de
desarrollo y aplica HATEOAS a tu
proyecto semestral
