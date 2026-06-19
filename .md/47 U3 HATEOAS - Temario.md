Temario HATEOAS (Hypermedia As The Engine Of Application State)
Usando Proyecto de Ejemplo Biblioteca

Proyecto Biblioteca

Los cambios se verán reflejados donde aparezca el distintivo [HATEOAS], o bien, [HATEOAS-INI] y [HATEOAS-FIN] para marcar bloques.

Archivos de configuración en C:\biblioteca

pom.xml. Al pom padre se agrega la dependencia de Spring Boot HATEOAS con sus versiones.

ms-XXXXXXX\pom.yml. A los pom de los módulos hijos se les agrega también la dependencia de Spring Boot HATEOAS.

Proyecto Catálogo

controller\LibroController.java. Se agregan los linkTo para libros y los CollectionModel para LibroResponse.

common\dto\LibroResponse.java. Se extiende el DTO Response de Libro (LibroResponse) de RepresentationModel<LibroResponse>y se
agrega lista de categorías.

Microservicio de Recursos

controller\RecursoFisicoController.java. Se agregan los linkTo para recurso y los CollectionModel para RecursoFisicoResponse.

common\dto\RecursoFisicoResponse.java. Se extiende el DTO Response de RecursoFisico (RecursoFisicoResponse) de
RepresentationModel<RecursoFisicoResponse>.

Microservicio de Usuarios

controller\UsuarioController.java. Se agregan los LinkTo para usuario y los CollectionModel para UsuarioResponse.

common\dto\UsuarioResponse.java. Se extiende el DTO Response de Usuario (UsuarioResponse) de
RepresentationModel<UsuarioResponse>.

Carpeta Postman

Se agrega ejemplo Ejemplo_Libros_con_HATEOAS.json

Carpeta Raíz

Se agrega REPORTE-HATEOAS.md.


