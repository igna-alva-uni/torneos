@echo off
echo ===== Iniciando Eureka Server =====
start "EUREKA" java -jar eureka\target\cl.duoc-eureka-1.0-SNAPSHOT.jar --spring.profiles.active=test

timeout /t 5 /nobreak > nul

echo ===== Iniciando Microservicios =====
start "MS-USUARIOS" java -jar ms-usuarios\target\cl.duoc-usuarios-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-AUTENTICACIONES" java -jar ms-autenticaciones\target\cl.duoc-autenticaciones-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-JUEGOS" java -jar ms-juegos\target\cl.duoc-juegos-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-TORNEOS" java -jar ms-torneos\target\cl.duoc-torneos-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-EQUIPOS" java -jar ms-equipos\target\cl.duoc-equipos-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-PARTIDAS" java -jar ms-partidas\target\cl.duoc-partidas-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-INSCRIPCIONES" java -jar ms-inscripciones\target\cl.duoc-inscripciones-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-RANKING" java -jar ms-ranking\target\cl.duoc-ranking-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-NOTIFICACIONES" java -jar ms-notificaciones\target\cl.duoc-notificaciones-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-ESTADISTICAS" java -jar ms-estadisticas\target\cl.duoc-estadisticas-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
rem Agrega aqui los demas microservicios si necesitas

echo Todos los servicios han sido lanzados.
