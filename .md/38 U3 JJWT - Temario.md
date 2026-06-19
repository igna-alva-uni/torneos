Temario Seguridad JWT Usando Proyecto de Ejemplo
Biblioteca

Proyecto Biblioteca
Los cambios se verán reflejados donde aparezca el distintivo [JJWT] (Java JWT), o bien, [JJWT-INI] y [JJWT-FIN] para marcar bloques.

Archivos de con?guración en C:\biblioteca
init-multi-db\01-usuarios.sql. Se aumenta password a VARCHAR (255) y se crea contraseña “Biblio@2026” con BCrypt para todos los
roles.

ms-XXXXXXX\src\main\resources\application.yml. Configura secret y expiration de JWT para cada uno de los microservicios.

pom.xml. Al pom padre se agregan Spring Security y dependencias JJWT con sus versiones.

Proyecto common: Componentes Compartidos de Seguridad
common\pom.xml. Se agregan Spring Security y dependencias JWT del ecosistema, usando las versiones del pom padre.

common\security\JwtProperties.java. Lee secret y expiración JWT desde application.yml de cada microservicio.

common\security\JwtTokenProvider.java. Genera, firma, valida tokens y extrae email, rol y nombre.

common\security\JwtAuthenticationFilter.java. Intercepta peticiones, valida Bearer Token (portador) e inyecta autenticación Spring.

common\security\FeignClientInterceptor.java. Propaga el token JWT en llamadas Feign entre microservicios protegidos.

common\exception\GlobalExceptionHandler.java. Unifica respuestas JSON para errores 401, 403, 404, 409 y validaciones.

common\CommonAutoConfiguration.java. Registra automáticamente beans common y habilita propiedades JwtProperties compartidas.

Microservicio Usuarios: Modelo, DTOs y Servicios de Autenticación
usuarios\model\Usuario.java. Modela cuenta, email, rol, estado activo y password BCrypt persistido.

usuarios\dto\LoginRequest.java. Recibe email y password enviados por el cliente al iniciar sesión.

usuarios\dto\RegisterRequest.java. Recibe datos mínimos para registrar nuevos clientes del sistema.

usuarios\dto\LoginResponse.java. Devuelve token JWT y datos seguros después de autenticar correctamente.

usuarios\service\UsuarioService.java. Gestiona usuarios y codifica passwords antes de guardar cambios.

usuarios\service\AuthService.java. Valida credenciales, controla accesos y emite tokens JWT firmados.

usuarios\controller\AuthController.java. Expone endpoints públicos para login y registro de usuarios.

Con?guración de Seguridad por Microservicio

usuarios\config\SecurityConfig.java. Define PasswordEncoder, sesiones stateless, rutas públicas y filtro JWT.

catalogo\config\SecurityConfig.java. Protege catálogo aplicando reglas RBAC sobre lectura y escritura de libros.

recursos\config\SecurityConfig.java. Protege recursos físicos y proyecciones usando roles desde el JWT.


