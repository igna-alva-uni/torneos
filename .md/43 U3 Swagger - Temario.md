Temario Documentación con Swagger Usando Proyecto de Ejemplo
Biblioteca

Proyecto Biblioteca

Los cambios se verán reflejados donde aparezca el distintivo [SWAGGER] (Java JWT), o bien, [SWAGGER-INI] y [SWAGGER-FIN] para marcar
bloques.

Archivos de configuración en C:\biblioteca

pom.xml. Al pom padre se agrega el grupo org.springdoc con el artefacto springdoc-openapi-starter-webmvc-ui y su versión, para poder usar
Swagger en los microservicios.

ms-XXXXXXX\pom.xml. En cada microservicio se agrega el grupo org.springdoc con el artefacto springdoc-openapi-starter-webmvc-ui.

ms-XXXXXXX\src\main\resources\application.yml. Se agrega swagger-ui para habilitar las páginas de documentación en cada microservicio.

ms-XXXXXXX\...\config\SecurityConfig.java. Se agrega un requestMatchers para permitir el uso de las rutas URL que permiten usar Swagger.

Proyecto common: Componentes Compartidos de Seguridad

common\pom.xml. Se agrega el grupo org.springdoc con el artefacto springdoc-openapi-starter-webmvc-ui.

common\config\OpenApiConfig.java. Se agrega este archivo que incorpora el “Botón Authorize” con un ícono de “Candado” para poder
ingresar el token entregado mediante la invocación al endpoint de login, y así poder usarlo en todas las siguientes invocaciones a cualquier
controlador desde Swagger UI.

common\security\JwtAuthenticationFilter.java. Se agrega el método shouldNotFilter para que se pueda usar la interfaz web de Swagger y no
se impida mostrarla debido a que ahora se usa autenticación con token.

common\security\JwtTokenProvider.java. Se agrega el método getExpirationFromToken que extrae la fecha de expiración del token y es
utilizado por el servicio de logout para saber cuándo un token en la blacklist puede ser limpiado.

common\security\TokenBlacklistService.java. Se agrega este archivo que permite administrar una blacklist para los tokens expirados, o
bien caducados mediante la ejecución del controlador de logout. Los tokens en la blacklist ya no se pueden usar para autenticar a un usuario.
Por simplicidad los tokens en la blacklist serán almacenados en memoria, aunque en entornos de producción con múltiples instancias de un
microservicio, el lugar más común para administrar estos tokens es Redis, que es una base de datos en memoria, que se levanta en un
contenedor Docker independiente.

Proyecto ms-usuarios

controller\AuthController.java. Se agrega el método logout que permite cerrar la sesión pasando el token a una blacklist.

Proyecto ms-catalogo

controller\LibroController.java. Usa anotaciones de Swagger como: @Operation, @ApiResponses, etc. Las anotaciones de Swagger
permiten complementar la documentación de la API en Swagger UI.


