@echo off
title Microservices Endpoint Test Runner
echo ======================================================================
echo             EJECUTANDO PRUEBAS DE ENDPOINTS DE MICROSERVICIOS
echo ======================================================================
echo.
powershell -ExecutionPolicy Bypass -File "%~dp0test-endpoint.ps1"
echo.
echo ======================================================================
echo  Pruebas finalizadas. Resultados escritos en: test_results.txt
echo ======================================================================
pause

