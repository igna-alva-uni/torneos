@echo off
title Microservices Endpoints Test Runner
echo =======================================================
echo          MICROSERVICES ENDPOINTS TEST RUNNER
echo =======================================================
echo.
echo Running PowerShell endpoint test script...
echo.
powershell -ExecutionPolicy Bypass -File "%~dp0test-endpoints.ps1"
echo.
echo =======================================================
echo Test run completed. Results written to test_results.txt
echo =======================================================
pause
