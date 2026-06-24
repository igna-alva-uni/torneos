@echo off
title Stop All Microservices
echo ======================================================================
echo             DETENIENDO TODOS LOS MICROSERVICIOS DE TORNEOS
echo ======================================================================
echo.
powershell -ExecutionPolicy Bypass -File "%~dp0stop-all.ps1"
echo.
echo ======================================================================
echo  Proceso finalizado. Todos los puertos de red han sido liberados.
echo ======================================================================
pause
