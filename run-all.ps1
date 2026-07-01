# run-all.ps1
# Script to launch Eureka Server and all Torneos microservices in the background

Write-Host "===== Starting Eureka Server =====" -ForegroundColor Cyan
Start-Process mvn -ArgumentList "-f eureka spring-boot:run" -NoNewWindow
Start-Sleep -Seconds 6

Write-Host "===== Starting Microservices =====" -ForegroundColor Cyan
$services = @("ms-usuarios", "ms-autenticaciones", "ms-juegos", "ms-torneos", "ms-equipos", "ms-partidas", "ms-inscripciones", "ms-ranking", "ms-notificaciones", "ms-estadisticas", "api-gateway")

foreach ($service in $services) {
    Write-Host "Launching $service..." -ForegroundColor Yellow
    Start-Process mvn -ArgumentList "-f $service spring-boot:run" -NoNewWindow
    Start-Sleep -Seconds 1
}

Write-Host "===== All services have been launched! Keeping script alive to preserve processes... =====" -ForegroundColor Green
while ($true) {
    Start-Sleep -Seconds 10
}
