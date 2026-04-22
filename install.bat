@echo off
echo.
echo === REINSTALACION DE DEPENDENCIAS MAVEN ===
echo.

REM Paso 1: Eliminar carpeta local de dependencias
echo Eliminando carpeta .m2 ...
rmdir /s /q %USERPROFILE%\.m2

REM Paso 2: Eliminar carpetas target de los proyectos
echo Eliminando carpetas target ...
rmdir /s /q C:\torneos\eureka\target
rmdir /s /q C:\torneos\ms-usuarios\target
rmdir /s /q C:\torneos\ms-autenticaciones\target
rmdir /s /q C:\torneos\ms-juegos\target
rmdir /s /q C:\torneos\ms-torneos\target
rmdir /s /q C:\torneos\ms-equipos\target
rmdir /s /q C:\torneos\ms-partidas\target
rmdir /s /q C:\torneos\ms-inscripciones\target
rmdir /s /q C:\torneos\ms-ranking\target
rmdir /s /q C:\torneos\ms-notificaciones\target
rmdir /s /q C:\torneos\ms-estadisticas\target

REM Paso 3: Instalar todas las dependencias forzadamente
echo Descargando dependencias nuevamente con Maven ...
mvn clean install -U -DskipTests

echo.
echo === PROCESO COMPLETADO ===
pause
