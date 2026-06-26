@echo off
echo ===== Iniciando Eureka Server =====
start "eureka" mvn -f eureka spring-boot:run

timeout /t 5 /nobreak > nul

echo ===== Iniciando Microservicios =====
start "ms-usuarios" mvn -f ms-usuarios spring-boot:run

start "ms-autenticaciones" mvn -f ms-autenticaciones spring-boot:run

start "ms-juegos" mvn -f ms-juegos spring-boot:run

start "ms-torneos" mvn -f ms-torneos spring-boot:run

start "ms-equipos" mvn -f ms-equipos spring-boot:run

start "ms-partidas" mvn -f ms-partidas spring-boot:run

start "ms-inscripciones" mvn -f ms-inscripciones spring-boot:run

start "ms-ranking" mvn -f ms-ranking spring-boot:run

start "ms-notificaciones" mvn -f ms-notificaciones spring-boot:run

start "ms-estadisticas" mvn -f ms-estadisticas spring-boot:run

start "api-gateway" mvn -f api-gateway spring-boot:run

rem Agrega aqui los demas microservicios si necesitas

echo Todos los servicios han sido lanzados.
