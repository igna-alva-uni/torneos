@echo off
setlocal

:MENU
cls
echo.
echo ============================================
echo         BIBLIOTECA - MENU PRINCIPAL
echo ============================================
echo.
echo   [1] Iniciar todos los servicios (dev)
echo   [2] Iniciar todos los servicios (test)
echo   [3] Compilar microservicios
echo   [4] Reinstalar dependencias Maven
echo.
echo   --- Servicios individuales ---
echo   [5] Iniciar Eureka
echo   [6] Iniciar ms-catalogo
echo   [7] Iniciar ms-recursos
echo   [8] Iniciar ms-usuarios
echo.
echo   [0] Salir
echo.
echo ============================================
set /p opcion="  Selecciona una opcion: "

if "%opcion%"=="1" goto RUN_ALL
if "%opcion%"=="2" goto RUN_TEST
if "%opcion%"=="3" goto COMPILE
if "%opcion%"=="4" goto INSTALL
if "%opcion%"=="5" goto RUN_EUREKA
if "%opcion%"=="6" goto RUN_CATALOGO
if "%opcion%"=="7" goto RUN_RECURSOS
if "%opcion%"=="8" goto RUN_USUARIOS
if "%opcion%"=="0" goto SALIR

echo.
echo   Opcion invalida. Intenta de nuevo.
timeout /t 2 /nobreak > nul
goto MENU

REM ============================================

:RUN_ALL
cls
echo.
echo ===== Iniciando Eureka Server =====
start "EUREKA" mvn -f eureka spring-boot:run
timeout /t 5 /nobreak > nul
echo ===== Iniciando Microservicios =====
start "MS-CATALOGO" mvn -f ms-catalogo spring-boot:run
start "MS-RECURSOS"  mvn -f ms-recursos spring-boot:run
start "MS-USUARIOS"  mvn -f ms-usuarios spring-boot:run
echo Todos los servicios han sido lanzados.
pause
goto MENU

:RUN_TEST
cls
echo.
echo ===== Iniciando Eureka Server (test) =====
start "EUREKA" java -jar eureka\target\eureka.jar --spring.profiles.active=test
timeout /t 5 /nobreak > nul
echo ===== Iniciando Microservicios (test) =====
start "MS-CATALOGO" java -jar ms-catalogo\target\ms-catalogo.jar --spring.profiles.active=test
start "MS-RECURSOS"  java -jar ms-recursos\target\ms-recursos.jar --spring.profiles.active=test
start "MS-USUARIOS"  java -jar ms-usuarios\target\ms-usuarios.jar --spring.profiles.active=test
echo Todos los servicios han sido lanzados en modo test.
pause
goto MENU

:COMPILE
cls
echo.
echo ===== Compilando microservicios =====
cd /d c:\biblioteca\ms-catalogo
call mvn clean install -U
cd /d c:\biblioteca\ms-recursos
call mvn clean install -U
cd /d c:\biblioteca\ms-usuarios
call mvn clean install -U
echo Compilacion completada.
pause
goto MENU

:INSTALL
cls
echo.
echo === REINSTALACION DE DEPENDENCIAS MAVEN ===
echo.
echo Eliminando carpeta .m2 ...
rmdir /s /q %USERPROFILE%\.m2
echo Eliminando carpetas target ...
rmdir /s /q C:\biblioteca\eureka\target
rmdir /s /q C:\biblioteca\ms-catalogo\target
rmdir /s /q C:\biblioteca\ms-recursos\target
rmdir /s /q C:\biblioteca\ms-usuarios\target
echo Descargando dependencias nuevamente con Maven ...
mvn clean install -U -DskipTests
echo.
echo === PROCESO COMPLETADO ===
pause
goto MENU

:RUN_EUREKA
cls
echo.
echo ===== Iniciando Eureka =====
start "EUREKA" mvn -f eureka spring-boot:run
echo Eureka iniciado.
pause
goto MENU

:RUN_CATALOGO
cls
echo.
echo ===== Iniciando ms-catalogo =====
start "MS-CATALOGO" mvn -f ms-catalogo spring-boot:run
echo ms-catalogo iniciado.
pause
goto MENU

:RUN_RECURSOS
cls
echo.
echo ===== Iniciando ms-recursos =====
start "MS-RECURSOS" mvn -f ms-recursos spring-boot:run
echo ms-recursos iniciado.
pause
goto MENU

:RUN_USUARIOS
cls
echo.
echo ===== Iniciando ms-usuarios =====
start "MS-USUARIOS" mvn -f ms-usuarios spring-boot:run
echo ms-usuarios iniciado.
pause
goto MENU

:SALIR
cls
echo.
echo   Hasta luego.
echo.
endlocal
exit /b