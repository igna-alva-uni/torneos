@echo off
echo.
echo === COMPILANDO MICROSERVICIOS ===
echo.
call cd C:\torneos\ms-usuarios
call mvn clean install -U
call cd C:\torneos\ms-autenticaciones
call mvn clean install -U
call cd C:\torneos\ms-juegos
call mvn clean install -U
call cd C:\torneos\ms-torneos
call mvn clean install -U
call cd C:\torneos\ms-equipos
call mvn clean install -U
call cd C:\torneos\ms-partidas
call mvn clean install -U
call cd C:\torneos\ms-inscripciones
call mvn clean install -U
call cd C:\torneos\ms-ranking
call mvn clean install -U
call cd C:\torneos\ms-notificaciones
call mvn clean install -U
call cd C:\torneos\ms-estadisticas
call mvn clean install -U
echo.
echo === COMPILACION COMPLETADA ===
pause
