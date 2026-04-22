@echo off
echo Descargando microservicios Spring Boot...
echo.
echo Descargando eureka.zip...
curl -o eureka.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=eureka&groupId=cl.duoc&artifactId=cl-duoc-eureka&name=torneos-eureka&description=servicio-eureka&packageName=cl.duoc.eureka&packaging=jar&javaVersion=21&dependencies=cloud-eureka-server,devtools"
echo.
echo Descargando ms-usuarios.zip...
curl -o ms-usuarios.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-usuarios&groupId=cl.duoc&artifactId=cl-duoc-usuarios&name=torneos-usuarios&description=servicio-usuarios&packageName=cl.duoc.usuarios&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-autenticaciones.zip...
curl -o ms-autenticaciones.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-autenticaciones&groupId=cl.duoc&artifactId=cl-duoc-autenticaciones&name=torneos-autenticaciones&description=servicio-autenticaciones&packageName=cl.duoc.autenticaciones&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-juegos.zip...
curl -o ms-juegos.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-juegos&groupId=cl.duoc&artifactId=cl-duoc-juegos&name=torneos-juegos&description=servicio-juegos&packageName=cl.duoc.juegos&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-torneos.zip...
curl -o ms-torneos.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-torneos&groupId=cl.duoc&artifactId=cl-duoc-torneos&name=torneos-torneos&description=servicio-torneos&packageName=cl.duoc.torneos&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-equipos.zip...
curl -o ms-equipos.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-equipos&groupId=cl.duoc&artifactId=cl-duoc-equipos&name=torneos-equipos&description=servicio-equipos&packageName=cl.duoc.equipos&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-partidas.zip...
curl -o ms-partidas.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-partidas&groupId=cl.duoc&artifactId=cl-duoc-partidas&name=torneos-partidas&description=servicio-partidas&packageName=cl.duoc.partidas&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-inscripciones.zip...
curl -o ms-inscripciones.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-inscripciones&groupId=cl.duoc&artifactId=cl-duoc-inscripciones&name=torneos-inscripciones&description=servicio-inscripciones&packageName=cl.duoc.inscripciones&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-ranking.zip...
curl -o ms-ranking.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-ranking&groupId=cl.duoc&artifactId=cl-duoc-ranking&name=torneos-ranking&description=servicio-ranking&packageName=cl.duoc.ranking&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-notificaciones.zip...
curl -o ms-notificaciones.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-notificaciones&groupId=cl.duoc&artifactId=cl-duoc-notificaciones&name=torneos-notificaciones&description=servicio-notificaciones&packageName=cl.duoc.notificaciones&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descargando ms-estadisticas.zip...
curl -o ms-estadisticas.zip "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.5.13&baseDir=ms-estadisticas&groupId=cl.duoc&artifactId=cl-duoc-estadisticas&name=torneos-estadisticas&description=servicio-estadisticas&packageName=cl.duoc.estadisticas&packaging=jar&javaVersion=21&dependencies=web,data-jpa,lombok,postgresql,cloud-feign"
echo.
echo Descarga completada.
pause
