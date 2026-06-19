Documentación de
Microservicios:
| OpenAPI | Specification | (OAS) |
| ------- | ------------- | ----- |
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
? Documentación con Swagger
? Implementación de HATEOAS en la Documentación de APIs
? Pruebas unitarias, análisis de testsy generación de datos con DataFaker
? API Gateway
? Despliegue local y remoto de microservicios

• Contenidos
| • ¿Qué es OpenAPI | Specification | (OAS)? |
| ----------------- | ------------- | ------ |
• Beneficios
• Swagger.
• Desarrollo de guía práctica.

| • Microservicios con OpenAPI | Specification | (OAS) |
| ---------------------------- | ------------- | ----- |
• OpenAPI Initiative es el consorcio industrial que supervisa el desarrollo de estándares abiertos para APIs.
• OpenAPI Specification (OAS) es su formato de descripción estándar.

| •                       | ¿Qué es OpenAPI |         | Specification | (OAS)? |
| ----------------------- | --------------- | ------- | ------------- | ------ |
| OpenAPI                 | Specification   | es una  |               |        |
| especificación estándar |                 | para    |               |        |
| describir APIs          | RESTful.        |         |               |        |
Permite a los desarrolladores definir
las operaciones disponibles, los datos
de entrada y salida, y otros aspectos
de la API de una manera que es tanto
legible por humanos como por
máquinas.
OAS es mantenido por la OpenAPI
Initiative, que es parte de la Linux
Foundation.

•
Beneficios de Usar OAS en Microservicios
DOCUMENTACIÓN ESTÁNDAR
| OAS  | proporciona  |     | una  | manera  | estandarizada  |     | de  |
| ---- | ------------ | --- | ---- | ------- | -------------- | --- | --- |
documentar las APIs, facilitando la comprensión y el uso
de los servicios por parte de otros desarrolladores.
INTEROPERABILIDAD
Debido a que OAS es independiente del lenguaje, permite
| que  | diferentes  |     | servicios  | desarrollados  |     | en  distintos  |     |
| ---- | ----------- | --- | ---------- | -------------- | --- | -------------- | --- |
lenguajes de programación interactúen sin problemas.
AUTOMATIZACIÓN
OAS  permite  la  generación  automática  de  clientes  API,
| servidores  |     | y  documentación,  |     | reduciendo  |     | el  esfuerzo  |     |
| ----------- | --- | ------------------ | --- | ----------- | --- | ------------- | --- |
manual y los errores.

| •         | ¿Qué es Swagger? |             |         |         |                  |             |            |     |     |
| --------- | ---------------- | ----------- | ------- | ------- | ---------------- | ----------- | ---------- | --- | --- |
| Es        | un               | conjunto    |         | de      | herramientas     |             |            |     | de  |
| software  |                  | de          | código  |         | abierto          |             | diseñadas  |     |     |
| para      | ayudar           |             |         | a  los  | desarrolladores  |             |            |     | a   |
| diseñar,  |                  | construir,  |         |         |                  | documentar  |            |     | y   |
consumir APIs RESTful.
| Se              | basa  | en  | la     | especificación  |             |                | OpenAPI  |     |     |
| --------------- | ----- | --- | ------ | --------------- | ----------- | -------------- | -------- | --- | --- |
| (anteriormente  |       |     |        | conocida        |             | como           | Swagger  |     |     |
| Specification)  |       |     | y      | se  ha          | convertido  |                |          | en  | un  |
| estándar        |       | de  | facto  | para            | la          | documentación  |          |     |     |
de APIs.
SWAGGER

• Rol de Swagger UI
Swagger UI es una herramienta que toma el
archivo de especificación OpenAPI y lo renderiza
visualmente en un sitio web interactivo y fácil de
usar.
• Desarrollo: Permite a los desarrolladores de
frontend y backend entender la API
inmediatamente.
• Comunicación: Sirve como la principal fuente de
verdad y documentación viva para los testers,
arquitectos y otros microservicios.
• Pruebas: Permite probar directamente los
endpoints desde la interfaz web.

• Anotaciones Clave en Spring Controllers
Anotación Propósito Ejemplo de uso
Agrupa y nombra un conjunto de endpoints (ej.
@Tag Sobre la clase Controller.
"Gestión de Usuarios").
Define el resumen y la descripción de un
@Operation Sobre el método Controller.
endpoint específico.
Documenta parámetros de ruta o consulta (ej.
@Parameter Dentro de @Operation.
GET /users/{id}).
Documenta el cuerpo de la petición (para Documenta el objeto DTO del
@RequestBody
POST/PUT). método.
Define los posibles códigos de respuesta HTTP y Dentro de @ApiResponses sobre
@ApiResponse
su contenido. el método.

• Cierre de esta presentación
• ¿Qué es OpenAPI Specification (OAS) ?
• ¿Cuáles son los Beneficios de OAS?
• ¿Para qué sirve Swagger?
• Desarrollo de guía práctica.

• Programa Fuente de Ejemplo de Biblioteca
1. Revisemos como Incorporar
Swagger a la Versión Actual de
Biblioteca (Apoyo mediante Video)
2. Usa el Proyecto de Biblioteca con
Swagger como patrón de desarrollo
y aplica Swagger a tu proyecto
semestral
