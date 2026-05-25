@echo off
rem Arranca Eureka en segundo plano si no está disponible en localhost:8761
powershell -Command "if (-not (Test-NetConnection -ComputerName 'localhost' -Port 8761 -InformationLevel Quiet)) { Start-Process -FilePath 'cmd.exe' -ArgumentList '/c','run-eureka.bat' -WindowStyle Minimized }"
rem Espera hasta 60s a que Eureka responda
powershell -Command "$i=0; while ($i -lt 60 -and -not (Test-NetConnection -ComputerName 'localhost' -Port 8761 -InformationLevel Quiet)) { Start-Sleep -Seconds 2; $i++ }; if ($i -ge 60) { Write-Host 'Eureka no respondió en 120s, intentando continuar...' }"

rem Intentar liberar el puerto 9010 (varios intentos) para evitar condiciones de carrera
for /L %%i in (1,1,5) do (
	for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":9010"') do (
		echo Puerto 9010 usado por PID %%a, intentando cerrar proceso...
		taskkill /PID %%a /F >nul 2>&1 || echo No se pudo terminar PID %%a
	)
	timeout /t 1 >nul
)

rem Comprobar si aún existe proceso en puerto 9010
set PID=
for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":9010"') do set PID=%%a
if defined PID (
	echo Aviso: puerto 9010 sigue en uso por PID %PID%, puede causar fallo al arrancar.
)

rem Buscar un puerto libre en el rango 9010-9020 y arrancar la aplicación en ese puerto
set PORT=
for /L %%P in (9010,1,9020) do (
	netstat -ano ^| findstr ":%%P" >nul || (
		set PORT=%%P
		goto :start_app
	)
)
:start_app
if not defined PORT (
	echo No se encontro puerto libre en el rango 9010-9020, usando 9010 por defecto
	set PORT=9010
)
echo Iniciando ms-estadisticas en el puerto %PORT% (nueva ventana)
start "ms-estadisticas" cmd /k mvn -Dspring-boot.run.arguments=--server.port=%PORT% -f ms-estadisticas spring-boot:run
