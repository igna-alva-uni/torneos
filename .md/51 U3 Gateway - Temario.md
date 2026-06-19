Temario  Api  Gateway  +  Eureka  Usando  Proyecto  de  Ejemplo
Biblioteca

Proyecto Biblioteca

Los cambios se verán reflejados donde aparezca el distintivo [GATEWAY], o bien, [GATEWAY-INI] y [GATEWAY-FIN] para marcar bloques.

Archivos de configuración en C:\biblioteca

pom.xml. Al pom padre se le agrega el módulo “api-gateway” para referenciar al nuevo proyecto Java llamado api-gateway. Se actualiza la
versión de Spring Boot por la 3.5.15.

.vscode\launch.json. Se corrige el archivo launch.json para permitir que arranquen al mismo tiempo en modo depuración: Eureka, API Gateway
y todos los microservicios (usuarios, catalogo y recursos).

.vscode\tasks.json. Se agrega para crear una tarea única, que permite esperar a que Eureka Server esté listo antes de ejecutar la API Gateway y
los microservicios, permitiendo así que se pueda usar "Debug All Services", ejecutando los proyectos Java en paralelo y sin errores.

launch.bat. Se le agrega la opción para poder arrancar API Gateway en consola.

ms-XXXXXXX\src\main\resources\application.yml. Se cambia la propiedad Server Port por el valor 0, para que al arrancar el microservicio se
asigne un puerto al azar y ya no queden los puertos 9000 en duro, de esta forma se pueden levantar varias instancias de un mismo microservicio.

Proyecto Common

common\server\InstanceHeaderFilter.java. Filtro HTTP que agrega el header de respuesta X-Instance-Port y X-Instance-Id para mostrar el
puerto y el nombre del microservicio que acaba de contestar un requerimiento de una aplicación cliente, por ejemplo, desde Postman.

common\server\ InstanceHeaderAutoConfiguration.java. Auto-configuración que registra {@link InstanceHeaderFilter} en cada microservicio
para que se puedan devolver los header del puerto y nombre del microservicio.

las  siguientes
main\resources\META-INF\spring\org.springframework.boot.autoconfigure.AutoConfiguration.imports.  Se  agregan
la  common:  cl.triskeledu.common.CommonAutoConfiguration  y
configuraciones  para  evitar  código  repetitivo  y  programarlo  en
cl.triskeledu.common.server.InstanceHeaderAutoConfiguration. No olvidar que la palabra triskeledu debe ser cambiada por el dominio de
tu proyecto.

Proyecto Java API Gateway

pom.xml. Se agrega el archivo pom.xml con las dependencias necesarias para API Gateway.

resources\application.yml. Se agrega el archivo yml con la configuración del API Gateway, corriendo en el puerto 9000. Este archivo permite el
enrutamiento  inteligente,  balanceo  de  carga  automático  y  descubrimiento  dinámico  de  servicios  mediante  Eureka  y  Spring  Cloud.  Además,
contiene  la  configuración  con  los  “predicates”,  que  son  condiciones  que  analizan  la  URL  entrante  para  determinar  exactamente  a  qué
microservicio debe redirigirse cada petición.

Carpeta Postman

Se agregan ejemplos de Postman y HATEOAS.

Carpeta docs

Se agrega documentación de Unidad 3.


