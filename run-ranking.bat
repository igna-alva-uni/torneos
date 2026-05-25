@echo off
rem Arranca Eureka en segundo plano si no está disponible en localhost:8761
powershell -Command "if (-not (Test-NetConnection -ComputerName 'localhost' -Port 8761 -InformationLevel Quiet)) { Start-Process -FilePath 'cmd.exe' -ArgumentList '/c','run-eureka.bat' -WindowStyle Minimized }"
rem Espera hasta 60s a que Eureka responda
powershell -Command "$i=0; while ($i -lt 60 -and -not (Test-NetConnection -ComputerName 'localhost' -Port 8761 -InformationLevel Quiet)) { Start-Sleep -Seconds 2; $i++ }; if ($i -ge 60) { Write-Host 'Eureka no respondió en 120s, intentando continuar...' }"

rem Buscar un puerto libre en el rango 9008-9015 y arrancar la aplicación en ese puerto
set PORT=
for /L %%P in (9008,1,9015) do (
	netstat -ano ^| findstr ":%%P" >nul || (
		set PORT=%%P
		goto :start_app
	)
)
:start_app
if not defined PORT (
	echo No se encontro puerto libre en el rango 9008-9015, usando 9008 por defecto
	set PORT=9008
)
echo Iniciando ms-ranking en el puerto %PORT% (nueva ventana)
start "ms-ranking" cmd /k "mvn -Dspring-boot.run.arguments=--server.port=%PORT% -f ms-ranking spring-boot:run"
exit /b 0
