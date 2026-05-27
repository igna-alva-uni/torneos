# stop-all.ps1
# Stops all Torneos Microservices and Eureka Server by terminating processes listening on their ports

$ErrorActionPreference = "SilentlyContinue"

Write-Host "======================================================================" -ForegroundColor Cyan
Write-Host "                STOPPING ALL MICROSERVICES & EUREKA                   " -ForegroundColor Cyan
Write-Host "======================================================================" -ForegroundColor Cyan
Write-Host ""

# Microservices Ports mapping
$services = @{
    8761 = "Eureka Server"
    9001 = "ms-usuarios"
    9002 = "ms-autenticaciones"
    9003 = "ms-juegos"
    9004 = "ms-torneos"
    9005 = "ms-equipos"
    9006 = "ms-partidas"
    9007 = "ms-inscripciones"
    9008 = "ms-ranking"
    9009 = "ms-notificaciones"
    9010 = "ms-estadisticas"
}

$killedCount = 0

foreach ($port in ($services.Keys | Sort-Object)) {
    $serviceName = $services[$port]
    
    # Get connections listening on the local port
    $connections = Get-NetTCPConnection -LocalPort $port -State Listen -ErrorAction SilentlyContinue
    
    if ($connections) {
        # Extract unique process IDs
        $pids = $connections | Select-Object -ExpandProperty OwningProcess -Unique
        
        foreach ($pid in $pids) {
            $proc = Get-Process -Id $pid -ErrorAction SilentlyContinue
            if ($proc) {
                Write-Host "Found [$serviceName] listening on port $port (PID: $pid, Name: $($proc.Name))" -ForegroundColor Yellow
                Write-Host "  --> Terminating process..." -ForegroundColor DarkYellow
                
                Stop-Process -Id $pid -Force -ErrorAction SilentlyContinue
                
                Write-Host "  --> Successfully stopped [$serviceName]!" -ForegroundColor Green
                $killedCount++
            }
        }
    } else {
        Write-Host "Service [$serviceName] on port $port is not running (already stopped)." -ForegroundColor Gray
    }
}

Write-Host ""
Write-Host "======================================================================" -ForegroundColor Cyan
if ($killedCount -gt 0) {
    Write-Host "  Process complete. Terminated $killedCount service process(es)." -ForegroundColor Green
} else {
    Write-Host "  Process complete. No active services found." -ForegroundColor White
}
Write-Host "======================================================================" -ForegroundColor Cyan
