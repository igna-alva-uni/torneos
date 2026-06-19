Spring Security
y JWT
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
? Seguridad y JJWT
? Documentación con Swagger
? Implementación de HATEOAS en la Documentación de APIs
? Pruebas unitarias, análisis de testsy generación de datos con DataFaker
? API Gateway
? Despliegue local y remoto de microservicios

• Contenidos
• JWT y Spring Security
• Json Web Token (JWT)
• Flujo para obtener un Token
• Flujo para ejecutar API Rest usando el Token
• Desarrollo de guía práctica.

• Seguridad Básica: JWT y Spring Security
| Spring          |          | Security    |             | es           | el        | framework    |     |        | de            | seguridad     |
| --------------- | -------- | ----------- | ----------- | ------------ | --------- | ------------ | --- | ------ | ------------- | ------------- |
| dominante.      |          | Proporciona |             |              | servicios |              |     | de     | autenticación |               |
| (¿quién         | eres?)   |             | y           | autorización |           | (¿qué        |     | puedes |               | hacer?).      |
| Spring          | Security |             |             | agrega:      |           |              |     |        |               |               |
| • Filtros       |          | que         | se ejecutan |              | antes     |              | de  | llegar |               | al Controller |
| • Manejo        |          | de          | usuarios    | y            | roles     |              |     |        |               |               |
| • Autenticación |          |             |             | (login)      | y         | autorización |     |        |               | (qué puede    |
ver/hacer)
| En Spring |                      | Boot | 3,  | se  | configura |     | usualmente |     |     | con una |
| --------- | -------------------- | ---- | --- | --- | --------- | --- | ---------- | --- | --- | ------- |
| clase     | SecurityFilterChain. |      |     |     |           |     |            |     |     |         |

• Seguridad Básica: JWT y Spring Security
JWT (JSON Web Token) es el estándar para la seguridad en microservicios. Es un token
codificado que contiene información de usuario y permisos, permitiendo la autenticación sin
| estado          | (Stateless), |     | ideal | para la escalabilidad |     | horizontal. |     |
| --------------- | ------------ | --- | ----- | --------------------- | --- | ----------- | --- |
| Características |              | de  | JWT   |                       |     |             |     |
1. Autocontenido: Contiene toda la información de usuario y roles necesaria (claims) para la
| autenticación |     |     | sin consultar | la base | de datos | en  | cada petición. |
| ------------- | --- | --- | ------------- | ------- | -------- | --- | -------------- |
2. Firmado (Signed): Utiliza una clave secreta (Secret Key) para generar una firma que
| garantiza |     | la integridad |     | del token | (que no | ha sido | alterado). |
| --------- | --- | ------------- | --- | --------- | ------- | ------- | ---------- |
3. Transmisión: Se envía típicamente en el encabezado Authorization con el prefijo Bearer
(Portador)

| •          | Seguridad Básica: JWT y Spring Security  |           |               |     |        |        |          |
| ---------- | ---------------------------------------- | --------- | ------------- | --- | ------ | ------ | -------- |
| Flujo      | básico:                                  |           |               |     |        |        |          |
| 1. Cliente | se                                       | autentica | ? /auth/login |     |        |        |          |
| 2. El      | servidor                                 | valida    | credenciales  | y   | genera | un JWT | firmado. |
3. El cliente guarda el JWT (por ejemplo, en localStorage o memoria).
| 4. En | cada | request | posterior | incluye | el token | en el | header. |
| ----- | ---- | ------- | --------- | ------- | -------- | ----- | ------- |
5. El filtro de Spring Security valida el token antes de llegar al Controller.
| Estructura | del | JWT |     |     |     |     |     |
| ---------- | --- | --- | --- | --- | --- | --- | --- |
Un JWT se compone de: Header, Payload y Signature en Base64URL y separadas por puntos (.)
HEADER.PAYLOAD.SIGNATURE
• Header (Cabecera): Indica el algoritmo de firma y el tipo de token
• Payload (Carga Útil): Contiene los claims (atributos), como: email, rol y nombre usuario.
• Signature (Firma): Contiene una cadena criptográfica que valida la autenticidad del mensaje.

• Java JWT (Json Web Token)
JWT impone un modelo Stateless (sin estado), donde el servidor no almacena ninguna información de
| sesión,      | sino que | toda | la identidad | del usuario        | reside | en el token | firmado. |
| ------------ | -------- | ---- | ------------ | ------------------ | ------ | ----------- | -------- |
| Deshabilitar | Sesiones |      | (El Pilar    | de la Arquitectura |        | Stateless): |          |
En Spring Security se usa la instrucción:
session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
para forzar la política de creación de sesiones sin estado. Esto instruye a Spring a ignorar y no crear
sesiones HTTP para las peticiones, desvinculando la autenticación del estado del servidor. Es el primer y
| más crítico | paso     | para          | una arquitectura | escalable. |     |     |     |
| ----------- | -------- | ------------- | ---------------- | ---------- | --- | --- | --- |
| Endpoint    | de Login | personalizado |                  |            |     |     |     |
Es un controlador REST que recibe el usuario y la contraseña (ej: /auth/login). Actúa como filtro principal
y delega la validación de estas credenciales al AuthenticationManager de Spring Security para verificar
| si el usuario | es  | válido. |     |     |     |     |     |
| ------------- | --- | ------- | --- | --- | --- | --- | --- |

Cliente
?
Flujo de
? Envía Email + Password (credenciales)
?
AuthController
Seguridad ?
? Recibe solicitud de login
?
AuthenticationManager valida credenciales
?
?? Busca usuario en BD por email
?? Valida password comparando hash
?? Obtiene rol (ADMIN, USER, etc.)
?
JwtService genera token JWT
?
?? Genera Claims (atributos) como: email, rol y expiración
?? Crea JWT
?? Firma con clave secreta de application.yml para integridad del token
?
JWT
?
? Token generado
?
AuthController
?
? Devuelve JWT en JSON
?
Cliente
?
? Guarda el token
?
Peticiones futuras

Cliente envía petición con JWT
?
Cadena de Filtros
? Authorization: Bearer JWT
?
en
JwtAuthenticationFilter revisa el token y autentica
?
Peticiones Futuras
?? Extrae el token
?? Valida el token
?? Obtiene email y rol
?? Crea Authentication
?
SecurityContextHolder guarda identidad del usuario
?
?
Spring Security consulta la identidad y autoriza
?
?? ¿Tiene permiso el rol?
?
?? Sí ? Accede al Controller
?? No ? Token inválido o inexistente ? Respuesta 401 Unauthorized

• Implementación en Spring Security
Autorización fina con @PreAuthorize
Después de validar el JWT, Spring Security carga la identidad del usuario y sus roles en el SecurityContext.
Con esa información, @PreAuthorize permite proteger métodos específicos según reglas de acceso.
• Se habilita con @EnableMethodSecurity
• Permite validar roles antes de ejecutar un método
• Evita que usuarios sin permiso accedan a operaciones sensibles
Ejemplo:
@PreAuthorize("hasRole('ADMIN')")
Idea clave:
@PreAuthorize controla quién puede ejecutar cada método.

3
1 2
Definición de variables en Postman:
De modo que podemos usar {{base_url_usuarios}}
como variable para definir la url de los request
de usuarios

Definición de script en Postman
para guardar el token en una variable
y poder usarlo en futuros request
por ejemplo el de “Listar todos los libros”

• Cierre de esta presentación
• ¿Qué es JWT y Spring Security?
• ¿Qué es un Json Web Token (JWT)?
• ¿Cuál es el flujo para obtener un Token?
• ¿Cuál es el flujo para ejecutar API Rest usando el Token?
• Desarrollo de guía práctica.

• Programa Fuente de Ejemplo de Biblioteca
1. Revisemos como Incorporar JJWT a
la Versión Actual de Biblioteca
(Apoyo mediante Video)
2. Usa el Proyecto de Biblioteca con
JJWT como patrón de desarrollo y
aplica seguridad a tu proyecto
semestral
