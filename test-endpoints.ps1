# test-endpoints.ps1
# Automating CRUD and endpoint testing for all 10 Torneos Microservices

$ErrorActionPreference = "Stop"

# Set encoding to UTF8 for clean output
$OutputEncoding = [System.Text.Encoding]::UTF8

# File to store results
$outputFile = Join-Path $PSScriptRoot "test_results.txt"

# Clear previous results if exists
if (Test-Path $outputFile) {
    Remove-Item $outputFile
}

# Logger helper to print to console and write to file simultaneously
function Log-Output {
    param(
        [string]$Message,
        [string]$Color = "White",
        [bool]$NoConsole = $false
    )
    if (-not $NoConsole) {
        Write-Host $Message -ForegroundColor $Color
    }
    $Message | Out-File -FilePath $outputFile -Append -Encoding utf8
}

# Banner
Log-Output "======================================================================" "Cyan"
Log-Output "         MICROSERVICES AUTOMATED ENDPOINTS & CRUD LIFECYCLE TEST      " "Cyan"
Log-Output "======================================================================" "Cyan"
Log-Output "Date/Time: $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')" "Gray"
Log-Output "OS: $(([Environment]::OSVersion).VersionString)" "Gray"
Log-Output "User: $env:USERNAME" "Gray"
Log-Output "Root Path: $PSScriptRoot" "Gray"
Log-Output "======================================================================" "Cyan"
Log-Output ""

# Define baseline list endpoints to test health status (corrected real paths and ports)
$baselineEndpoints = @(
    # Eureka
    @{ Service = "eureka"; Url = "http://localhost:8761/"; Method = "GET"; Description = "Eureka Service Discovery Dashboard" },

    # ms-usuarios
    @{ Service = "ms-usuarios"; Url = "http://localhost:9001/api/v1/usuarios/usuarios"; Method = "GET"; Description = "List users" },
    @{ Service = "ms-usuarios"; Url = "http://localhost:9001/api/v1/usuarios/paises"; Method = "GET"; Description = "List countries" },
    @{ Service = "ms-usuarios"; Url = "http://localhost:9001/api/v1/usuarios/perfiles"; Method = "GET"; Description = "List user profiles" },

    # ms-autenticaciones
    @{ Service = "ms-autenticaciones"; Url = "http://localhost:9002/api/v1/autenticaciones/roles"; Method = "GET"; Description = "List security roles" },

    # ms-juegos (port 9003)
    @{ Service = "ms-juegos"; Url = "http://localhost:9003/api/v1/juegos"; Method = "GET"; Description = "List games" },

    # ms-torneos (port 9004)
    @{ Service = "ms-torneos"; Url = "http://localhost:9004/api/v1/torneos"; Method = "GET"; Description = "List tournaments" },

    # ms-equipos
    @{ Service = "ms-equipos"; Url = "http://localhost:9005/api/v1/equipos/equipos"; Method = "GET"; Description = "List teams" },
    @{ Service = "ms-equipos"; Url = "http://localhost:9005/api/v1/equipos/miembros"; Method = "GET"; Description = "List team members" },
    @{ Service = "ms-equipos"; Url = "http://localhost:9005/api/v1/equipos/roles"; Method = "GET"; Description = "List team roles" },

    # ms-partidas (port 9006)
    @{ Service = "ms-partidas"; Url = "http://localhost:9006/api/v1/partidas"; Method = "GET"; Description = "List matches" },

    # ms-inscripciones (port 9007)
    @{ Service = "ms-inscripciones"; Url = "http://localhost:9007/api/v1/inscripciones"; Method = "GET"; Description = "List registrations" },

    # ms-ranking
    @{ Service = "ms-ranking"; Url = "http://localhost:9008/api/v1/rankings/rankings"; Method = "GET"; Description = "List rankings" },
    @{ Service = "ms-ranking"; Url = "http://localhost:9008/api/v1/rankings/tipos"; Method = "GET"; Description = "List ranking types" },
    @{ Service = "ms-ranking"; Url = "http://localhost:9008/api/v1/rankings/registros"; Method = "GET"; Description = "List ranking records" },

    # ms-notificaciones
    @{ Service = "ms-notificaciones"; Url = "http://localhost:9009/api/v1/notificaciones/tipos"; Method = "GET"; Description = "List notification types" },
    @{ Service = "ms-notificaciones"; Url = "http://localhost:9009/api/v1/notificaciones"; Method = "GET"; Description = "List notifications" },

    # ms-estadisticas
    @{ Service = "ms-estadisticas"; Url = "http://localhost:9010/api/v1/stats/equipos"; Method = "GET"; Description = "List team stats" },
    @{ Service = "ms-estadisticas"; Url = "http://localhost:9010/api/v1/stats/players"; Method = "GET"; Description = "List player stats" },
    @{ Service = "ms-estadisticas"; Url = "http://localhost:9010/api/v1/stats/partidas"; Method = "GET"; Description = "List match stats" }
)

$stats = @{
    Total = 0
    Success = 0
    Failed = 0
    Offline = 0
}

# Group services status
$servicesStatus = @{}

# General HTTP requester helper
function Invoke-HttpCall {
    param(
        [string]$Service,
        [string]$Method,
        [string]$Url,
        [string]$Body = $null,
        [string]$Description
    )

    Log-Output "Testing endpoint: [$Service] $Method $Url ($Description)..." -Color "Yellow"
    
    $stopwatch = [System.Diagnostics.Stopwatch]::StartNew()
    $response = $null
    $statusCode = $null
    $statusDescription = ""
    $isSuccess = $false
    $responseBody = ""
    $isOffline = $false

    try {
        $params = @{
            Uri = $Url
            Method = $Method
            TimeoutSec = 5
            UseBasicParsing = $true
        }
        if ($Body) {
            $params.Body = $Body
            $params.ContentType = "application/json"
        }
        
        $response = Invoke-WebRequest @params
        $stopwatch.Stop()
        
        $statusCode = [int]$response.StatusCode
        $statusDescription = $response.StatusDescription
        $responseBody = $response.Content
        $isSuccess = ($statusCode -ge 200 -and $statusCode -lt 300)
    }
    catch {
        $stopwatch.Stop()
        if ($_.Exception.Response) {
            $statusCode = [int]$_.Exception.Response.StatusCode
            $statusDescription = $_.Exception.Response.StatusDescription
            $isSuccess = $false
            
            try {
                $stream = $_.Exception.Response.GetResponseStream()
                $reader = New-Object System.IO.StreamReader($stream)
                $responseBody = $reader.ReadToEnd()
            } catch {
                $responseBody = "Could not read error response body."
            }
        }
        else {
            $isOffline = $true
            $statusCode = "CONNECTION_REFUSED"
            $statusDescription = "Service is offline / unavailable"
            $responseBody = $_.Exception.Message
        }
    }

    $elapsed = $stopwatch.ElapsedMilliseconds

    # Format JSON nicely if possible
    $formattedJson = $null
    $parsedJson = $null
    if (-not [string]::IsNullOrEmpty($responseBody)) {
        try {
            $parsedJson = ConvertFrom-Json $responseBody -ErrorAction SilentlyContinue
            if ($parsedJson) {
                $formattedJson = ConvertTo-Json $parsedJson -Depth 5
            }
        } catch {}
    }
    if (-not $formattedJson) {
        $formattedJson = $responseBody
    }

    # Truncate output to avoid massive log file
    $maxLogLength = 1000
    $truncatedJson = $formattedJson
    if ($truncatedJson.Length -gt $maxLogLength) {
        $truncatedJson = $truncatedJson.Substring(0, $maxLogLength) + "`r`n... [TRUNCATED - Response body is too long] ..."
    }

    # Output result to console
    if ($isOffline) {
        Log-Output "  --> Result: OFFLINE ($statusCode) in $elapsed ms" "Red"
    }
    elseif ($isSuccess) {
        Log-Output "  --> Result: ONLINE/SUCCESS ($statusCode $statusDescription) in $elapsed ms" "Green"
    }
    else {
        Log-Output "  --> Result: ERROR ($statusCode $statusDescription) in $elapsed ms" "Red"
    }

    # Write detailed block to report file
    Log-Output "" -NoConsole $true
    Log-Output "------------------------------------------------------------" -NoConsole $true
    Log-Output "ENDPOINT: [$Service] $Method $Url" -NoConsole $true
    Log-Output "Description: $Description" -NoConsole $true
    if ($Body) {
        Log-Output "Request Payload: $Body" -NoConsole $true
    }
    Log-Output "Status: $(if ($isOffline) { 'OFFLINE' } elseif ($isSuccess) { 'SUCCESS' } else { 'FAILED' })" -NoConsole $true
    Log-Output "HTTP Code: $statusCode ($statusDescription)" -NoConsole $true
    Log-Output "Response Time: $elapsed ms" -NoConsole $true
    Log-Output "Response Payload:" -NoConsole $true
    Log-Output $truncatedJson -NoConsole $true
    Log-Output "------------------------------------------------------------" -NoConsole $true
    Log-Output "" -NoConsole $true

    return [PSCustomObject]@{
        IsSuccess = $isSuccess
        IsOffline = $isOffline
        StatusCode = $statusCode
        ResponseBody = $responseBody
        ParsedJson = $parsedJson
        Elapsed = $elapsed
    }
}

# Helper to read JSON field safely
function Get-JsonField {
    param($Obj, $FieldName)
    if ($Obj -and $Obj.PSObject.Properties[$FieldName]) {
        return $Obj.$FieldName
    }
    return $null
}

# Helper to verify if an item with specific ID exists in a list endpoint
function Verify-ItemInList {
    param(
        [string]$Service,
        [string]$Url,
        [int]$ExpectedId,
        [string]$IdField = "id",
        [string]$NameField = $null,
        [string]$ExpectedNameValue = $null,
        [bool]$ExpectPresence = $true
    )

    $presenceStr = "absence"
    if ($ExpectPresence) { $presenceStr = "presence" }
    $listRes = Invoke-HttpCall $Service "GET" $Url $null "GET full list to verify item $presenceStr"
    $found = $false
    $matchingName = $false

    if ($listRes.ParsedJson) {
        $items = $listRes.ParsedJson
        if ($items -isnot [array]) {
            if ($items.PSObject.Properties['content']) {
                $items = $items.content
            } elseif ($items -is [PSCustomObject] -and $items.PSObject.Properties[$IdField]) {
                $items = @($items)
            }
        }

        foreach ($item in $items) {
            $itemId = Get-JsonField $item $IdField
            if ($itemId -ne $null -and [int]$itemId -eq $ExpectedId) {
                $found = $true
                if ($NameField) {
                    $val = Get-JsonField $item $NameField
                    if ($val -eq $ExpectedNameValue) {
                        $matchingName = $true
                    }
                }
                break
            }
        }
    }

    if ($ExpectPresence) {
        if ($found) {
            if ($NameField) {
                if ($matchingName) {
                    Log-Output "  --> VERIFICATION SUCCESS: Item $IdField=$ExpectedId with $NameField='$ExpectedNameValue' found in list!" "Green"
                    return $true
                } else {
                    Log-Output "  --> VERIFICATION FAILED: Item $IdField=$ExpectedId found, but $NameField does NOT match '$ExpectedNameValue'!" "Red"
                    return $false
                }
            } else {
                Log-Output "  --> VERIFICATION SUCCESS: Item $IdField=$ExpectedId found in list!" "Green"
                return $true
            }
        } else {
            Log-Output "  --> VERIFICATION FAILED: Item $IdField=$ExpectedId NOT found in list!" "Red"
            return $false
        }
    } else {
        if (-not $found) {
            Log-Output "  --> VERIFICATION SUCCESS: Item $IdField=$ExpectedId is indeed absent from list!" "Green"
            return $true
        } else {
            Log-Output "  --> VERIFICATION FAILED: Item $IdField=$ExpectedId is STILL present in list!" "Red"
            return $false
        }
    }
}

# ======================================================================
# PHASE 1: BASELINE HEALTH CHECK
# ======================================================================
Log-Output "======================================================================" "Cyan"
Log-Output "                 PHASE 1: BASELINE HEALTH STATUS CHECKS               " "Cyan"
Log-Output "======================================================================" "Cyan"
Log-Output ""

foreach ($ep in $baselineEndpoints) {
    $stats.Total++
    $serviceName = $ep.Service
    
    $res = Invoke-HttpCall -Service $serviceName -Method $ep.Method -Url $ep.Url -Description $ep.Description
    
    if ($res.IsOffline) {
        $stats.Offline++
        $servicesStatus[$serviceName] = "OFFLINE"
    }
    elseif ($res.IsSuccess) {
        $stats.Success++
        if (-not $servicesStatus.ContainsKey($serviceName) -or $servicesStatus[$serviceName] -ne "OFFLINE") {
            $servicesStatus[$serviceName] = "ONLINE"
        }
    }
    else {
        $stats.Failed++
        if (-not $servicesStatus.ContainsKey($serviceName) -or $servicesStatus[$serviceName] -ne "OFFLINE") {
            $servicesStatus[$serviceName] = "ONLINE (but endpoint returned error)"
        }
    }
}

# ======================================================================
# PHASE 2: CRUD & INTEGRATION LIFECYCLE TESTS (For ONLINE Services)
# ======================================================================
Log-Output ""
Log-Output "======================================================================" "Cyan"
Log-Output "                 PHASE 2: FULL CRUD LIFECYCLE TESTS                   " "Cyan"
Log-Output "======================================================================" "Cyan"
Log-Output "Running CRUD integration tests for active microservices..." "Gray"
Log-Output ""

# 1. ms-usuarios CRUD Test (usuarios, paises, perfiles)
if ($servicesStatus["ms-usuarios"] -eq "ONLINE") {
    Log-Output "`n>>> Starting CRUD tests for ms-usuarios..." "Cyan"
    
    # 1.1 Resource: usuarios
    Log-Output "Testing Resource: usuarios..." "Cyan"
    $body = '{"username": "Faker_CRUD", "email": "fakercrud@esports.com"}'
    $res = Invoke-HttpCall "ms-usuarios" "POST" "http://localhost:9001/api/v1/usuarios/usuarios" $body "Create user Faker_CRUD"
    $userId = Get-JsonField $res.ParsedJson "idUsuario"
    if (-not $userId) { $userId = Get-JsonField $res.ParsedJson "id" }
    
    if ($userId) {
        Invoke-HttpCall "ms-usuarios" "GET" "http://localhost:9001/api/v1/usuarios/usuarios/$userId" $null "GET user by ID"
        Verify-ItemInList "ms-usuarios" "http://localhost:9001/api/v1/usuarios/usuarios" $userId "idUsuario" "username" "Faker_CRUD" $true
        
        $putBody = '{"username": "Faker_CRUD_Updated", "email": "fakercrud@esports.com"}'
        Invoke-HttpCall "ms-usuarios" "PUT" "http://localhost:9001/api/v1/usuarios/usuarios/$userId" $putBody "Update user name"
        Verify-ItemInList "ms-usuarios" "http://localhost:9001/api/v1/usuarios/usuarios" $userId "idUsuario" "username" "Faker_CRUD_Updated" $true
        
        Invoke-HttpCall "ms-usuarios" "DELETE" "http://localhost:9001/api/v1/usuarios/usuarios/$userId" $null "DELETE user"
        Verify-ItemInList "ms-usuarios" "http://localhost:9001/api/v1/usuarios/usuarios" $userId "idUsuario" $null $null $false
    }

    # 1.2 Resource: paises
    Log-Output "Testing Resource: paises..." "Cyan"
    $body = '{"nombrePais": "test_pais_xyz", "codigoPais": "xyz"}'
    $res = Invoke-HttpCall "ms-usuarios" "POST" "http://localhost:9001/api/v1/usuarios/paises" $body "Create country test_pais_xyz"
    $paisId = Get-JsonField $res.ParsedJson "idPais"
    if (-not $paisId) { $paisId = Get-JsonField $res.ParsedJson "id" }
    
    if ($paisId) {
        Invoke-HttpCall "ms-usuarios" "GET" "http://localhost:9001/api/v1/usuarios/paises/$paisId" $null "GET country by ID"
        Invoke-HttpCall "ms-usuarios" "GET" "http://localhost:9001/api/v1/usuarios/paises/codigo/xyz" $null "GET country by code xyz"
        Invoke-HttpCall "ms-usuarios" "GET" "http://localhost:9001/api/v1/usuarios/paises/nombre/test_pais_xyz" $null "GET country by name test_pais_xyz"
        Verify-ItemInList "ms-usuarios" "http://localhost:9001/api/v1/usuarios/paises" $paisId "idPais" "nombrePais" "test_pais_xyz" $true
        
        $putBody = '{"nombrePais": "test_pais_updated", "codigoPais": "xyz"}'
        Invoke-HttpCall "ms-usuarios" "PUT" "http://localhost:9001/api/v1/usuarios/paises/$paisId" $putBody "Update country to test_pais_updated"
        
        Invoke-HttpCall "ms-usuarios" "DELETE" "http://localhost:9001/api/v1/usuarios/paises/$paisId" $null "DELETE country"
        Verify-ItemInList "ms-usuarios" "http://localhost:9001/api/v1/usuarios/paises" $paisId "idPais" $null $null $false
    }

    # 1.3 Resource: perfiles
    Log-Output "Testing Resource: perfiles..." "Cyan"
    $body = '{"idUsuario": 999, "nickname": "ProfileCRUD", "urlAvatar": "http://avatar.com/crud", "idPais": 1}'
    $res = Invoke-HttpCall "ms-usuarios" "POST" "http://localhost:9001/api/v1/usuarios/perfiles" $body "Create profile ProfileCRUD"
    $perfilId = Get-JsonField $res.ParsedJson "idUsuario" # the profile ID is idUsuario
    if (-not $perfilId) { $perfilId = Get-JsonField $res.ParsedJson "id" }
    
    if ($perfilId) {
        Invoke-HttpCall "ms-usuarios" "GET" "http://localhost:9001/api/v1/usuarios/perfiles/$perfilId" $null "GET profile by ID"
        Verify-ItemInList "ms-usuarios" "http://localhost:9001/api/v1/usuarios/perfiles" $perfilId "idUsuario" "nickname" "ProfileCRUD" $true
        
        $putBody = '{"idUsuario": 999, "nickname": "ProfileCRUD_Upd", "urlAvatar": "http://avatar.com/crud2", "idPais": 1}'
        Invoke-HttpCall "ms-usuarios" "PUT" "http://localhost:9001/api/v1/usuarios/perfiles/$perfilId" $putBody "Update profile"
        
        Invoke-HttpCall "ms-usuarios" "DELETE" "http://localhost:9001/api/v1/usuarios/perfiles/$perfilId" $null "DELETE profile"
        Verify-ItemInList "ms-usuarios" "http://localhost:9001/api/v1/usuarios/perfiles" $perfilId "idUsuario" $null $null $false
    }
}

# 2. ms-autenticaciones CRUD Test (roles, usuarios)
if ($servicesStatus["ms-autenticaciones"] -eq "ONLINE") {
    Log-Output "`n>>> Starting CRUD tests for ms-autenticaciones..." "Cyan"
    
    # 2.1 Resource: roles
    Log-Output "Testing Resource: roles..." "Cyan"
    $body = '{"nombreRol": "test_rol_xyz"}'
    $res = Invoke-HttpCall "ms-autenticaciones" "POST" "http://localhost:9002/api/v1/autenticaciones/roles" $body "Create role test_rol_xyz"
    $rolId = Get-JsonField $res.ParsedJson "idRol"
    if (-not $rolId) { $rolId = Get-JsonField $res.ParsedJson "id" }
    
    if ($rolId) {
        Invoke-HttpCall "ms-autenticaciones" "GET" "http://localhost:9002/api/v1/autenticaciones/roles/$rolId" $null "GET role by ID"
        Verify-ItemInList "ms-autenticaciones" "http://localhost:9002/api/v1/autenticaciones/roles" $rolId "idRol" "nombreRol" "test_rol_xyz" $true
        
        $putBody = '{"nombreRol": "test_rol_updated"}'
        Invoke-HttpCall "ms-autenticaciones" "PUT" "http://localhost:9002/api/v1/autenticaciones/roles/$rolId" $putBody "Update role to test_rol_updated"
        
        Invoke-HttpCall "ms-autenticaciones" "DELETE" "http://localhost:9002/api/v1/autenticaciones/roles/$rolId" $null "DELETE role"
        Verify-ItemInList "ms-autenticaciones" "http://localhost:9002/api/v1/autenticaciones/roles" $rolId "idRol" $null $null $false
    }

    # 2.2 Resource: usuarios (security profiles)
    Log-Output "Testing Resource: usuarios..." "Cyan"
    $body = '{"idUsuario": 999, "email": "authcrud@esports.com", "password": "Password123!", "nombresRoles": ["ROLE_USER"]}'
    $res = Invoke-HttpCall "ms-autenticaciones" "POST" "http://localhost:9002/api/v1/autenticaciones/usuarios" $body "Create security user credentials for ID 999"
    $secUserId = Get-JsonField $res.ParsedJson "idUsuario"
    if (-not $secUserId) { $secUserId = Get-JsonField $res.ParsedJson "id" }
    
    if ($secUserId) {
        Invoke-HttpCall "ms-autenticaciones" "GET" "http://localhost:9002/api/v1/autenticaciones/usuarios/$secUserId" $null "GET security user credentials by ID"
        
        $putBody = '{"idUsuario": 999, "email": "authcrud_upd@esports.com", "password": "Password123Updated!", "nombresRoles": ["ROLE_ADMIN"]}'
        Invoke-HttpCall "ms-autenticaciones" "PUT" "http://localhost:9002/api/v1/autenticaciones/usuarios/$secUserId" $putBody "Update security user credentials"
        
        Invoke-HttpCall "ms-autenticaciones" "DELETE" "http://localhost:9002/api/v1/autenticaciones/usuarios/$secUserId" $null "DELETE security user credentials"
    }
}

# 3. ms-juegos CRUD Test (juegos - port 9003)
if ($servicesStatus["ms-juegos"] -eq "ONLINE") {
    Log-Output "`n>>> Starting CRUD test for ms-juegos (juegos)..." "Cyan"
    
    $body = '{"nombre": "test_juego_xyz", "idGenero": 1, "descripcion": "test", "plataformas": [1]}'
    $res = Invoke-HttpCall "ms-juegos" "POST" "http://localhost:9003/api/v1/juegos" $body "Create game test_juego_xyz"
    $juegoId = Get-JsonField $res.ParsedJson "id"
    
    if ($juegoId) {
        Invoke-HttpCall "ms-juegos" "GET" "http://localhost:9003/api/v1/juegos/$juegoId" $null "GET game by ID"
        Verify-ItemInList "ms-juegos" "http://localhost:9003/api/v1/juegos" $juegoId "id" "nombre" "test_juego_xyz" $true
        
        $putBody = '{"nombre": "test_juego_updated", "idGenero": 1, "descripcion": "test_updated", "plataformas": [1]}'
        Invoke-HttpCall "ms-juegos" "PUT" "http://localhost:9003/api/v1/juegos/$juegoId" $putBody "Update game name to test_juego_updated"
        Verify-ItemInList "ms-juegos" "http://localhost:9003/api/v1/juegos" $juegoId "id" "nombre" "test_juego_updated" $true
        
        Invoke-HttpCall "ms-juegos" "DELETE" "http://localhost:9003/api/v1/juegos/$juegoId" $null "DELETE game"
        Verify-ItemInList "ms-juegos" "http://localhost:9003/api/v1/juegos" $juegoId "id" $null $null $false
    }
}

# 4. ms-torneos CRUD Test (torneos - port 9004)
if ($servicesStatus["ms-torneos"] -eq "ONLINE") {
    Log-Output "`n>>> Starting CRUD test for ms-torneos (torneos)..." "Cyan"
    
    $body = '{"nombre": "test_torneo_xyz", "idJuego": 1, "idFormato": 1, "fechaInicio": "2026-06-01", "fechaTermino": "2026-06-10"}'
    $res = Invoke-HttpCall "ms-torneos" "POST" "http://localhost:9004/api/v1/torneos" $body "Create tournament test_torneo_xyz"
    $torneoId = Get-JsonField $res.ParsedJson "id"
    
    if ($torneoId) {
        Invoke-HttpCall "ms-torneos" "GET" "http://localhost:9004/api/v1/torneos/$torneoId" $null "GET tournament by ID"
        Invoke-HttpCall "ms-torneos" "GET" "http://localhost:9004/api/v1/torneos/juego/1" $null "GET tournaments by juego ID 1"
        Verify-ItemInList "ms-torneos" "http://localhost:9004/api/v1/torneos" $torneoId "id" "nombre" "test_torneo_xyz" $true
        
        $putBody = '{"nombre": "test_torneo_updated", "idJuego": 1, "idFormato": 1, "fechaInicio": "2026-06-01", "fechaTermino": "2026-06-15"}'
        Invoke-HttpCall "ms-torneos" "PUT" "http://localhost:9004/api/v1/torneos/$torneoId" $putBody "Update tournament to test_torneo_updated"
        Verify-ItemInList "ms-torneos" "http://localhost:9004/api/v1/torneos" $torneoId "id" "nombre" "test_torneo_updated" $true
        
        Invoke-HttpCall "ms-torneos" "DELETE" "http://localhost:9004/api/v1/torneos/$torneoId" $null "DELETE tournament"
        Verify-ItemInList "ms-torneos" "http://localhost:9004/api/v1/torneos" $torneoId "id" $null $null $false
    }
}

# 5. ms-equipos CRUD Test (equipos, miembros, roles)
if ($servicesStatus["ms-equipos"] -eq "ONLINE") {
    Log-Output "`n>>> Starting CRUD tests for ms-equipos..." "Cyan"
    
    # 5.1 Resource: equipos
    Log-Output "Testing Resource: equipos..." "Cyan"
    $body = '{"nombreEquipo": "T400 Esports"}'
    $res = Invoke-HttpCall "ms-equipos" "POST" "http://localhost:9005/api/v1/equipos/equipos" $body "Create team T400 Esports"
    $equipoId = Get-JsonField $res.ParsedJson "idEquipo"
    if (-not $equipoId) { $equipoId = Get-JsonField $res.ParsedJson "id" }
    
    if ($equipoId) {
        Invoke-HttpCall "ms-equipos" "GET" "http://localhost:9005/api/v1/equipos/equipos/$equipoId" $null "GET team by ID"
        Verify-ItemInList "ms-equipos" "http://localhost:9005/api/v1/equipos/equipos" $equipoId "idEquipo" "nombreEquipo" "T400 Esports" $true
        
        $putBody = '{"nombreEquipo": "T1 Esports"}'
        Invoke-HttpCall "ms-equipos" "PUT" "http://localhost:9005/api/v1/equipos/equipos/$equipoId" $putBody "Update team name"
        Verify-ItemInList "ms-equipos" "http://localhost:9005/api/v1/equipos/equipos" $equipoId "idEquipo" "nombreEquipo" "T1 Esports" $true
        
        Invoke-HttpCall "ms-equipos" "DELETE" "http://localhost:9005/api/v1/equipos/equipos/$equipoId" $null "DELETE team"
        Verify-ItemInList "ms-equipos" "http://localhost:9005/api/v1/equipos/equipos" $equipoId "idEquipo" $null $null $false
    }

    # 5.2 Resource: roles
    Log-Output "Testing Resource: roles..." "Cyan"
    $body = '{"nombreRolEquipo": "test_rol_xyz"}'
    $res = Invoke-HttpCall "ms-equipos" "POST" "http://localhost:9005/api/v1/equipos/roles" $body "Create team role test_rol_xyz"
    $rolId = Get-JsonField $res.ParsedJson "idRolEquipo"
    if (-not $rolId) { $rolId = Get-JsonField $res.ParsedJson "id" }
    
    if ($rolId) {
        Invoke-HttpCall "ms-equipos" "GET" "http://localhost:9005/api/v1/equipos/roles/$rolId" $null "GET team role by ID"
        Verify-ItemInList "ms-equipos" "http://localhost:9005/api/v1/equipos/roles" $rolId "idRolEquipo" "nombreRolEquipo" "test_rol_xyz" $true
        
        $putBody = '{"nombreRolEquipo": "test_rol_updated"}'
        Invoke-HttpCall "ms-equipos" "PUT" "http://localhost:9005/api/v1/equipos/roles/$rolId" $putBody "Update team role to test_rol_updated"
        Verify-ItemInList "ms-equipos" "http://localhost:9005/api/v1/equipos/roles" $rolId "idRolEquipo" "nombreRolEquipo" "test_rol_updated" $true
        
        Invoke-HttpCall "ms-equipos" "DELETE" "http://localhost:9005/api/v1/equipos/roles/$rolId" $null "DELETE team role"
        Verify-ItemInList "ms-equipos" "http://localhost:9005/api/v1/equipos/roles" $rolId "idRolEquipo" $null $null $false
    }

    # 5.3 Resource: miembros
    Log-Output "Testing Resource: miembros..." "Cyan"
    $body = '{"idUsuario": 999, "idEquipo": 1, "idRolEquipo": 1}'
    $res = Invoke-HttpCall "ms-equipos" "POST" "http://localhost:9005/api/v1/equipos/miembros" $body "Create team member assignment"
    $miembroId = Get-JsonField $res.ParsedJson "idMiembro"
    if (-not $miembroId) { $miembroId = Get-JsonField $res.ParsedJson "id" }
    
    if ($miembroId) {
        Invoke-HttpCall "ms-equipos" "GET" "http://localhost:9005/api/v1/equipos/miembros/equipo/1" $null "GET members of team ID 1"
        
        $putBody = '{"idUsuario": 999, "idEquipo": 1, "idRolEquipo": 2}'
        Invoke-HttpCall "ms-equipos" "PUT" "http://localhost:9005/api/v1/equipos/miembros/$miembroId" $putBody "Update team member role"
        
        Invoke-HttpCall "ms-equipos" "DELETE" "http://localhost:9005/api/v1/equipos/miembros/$miembroId" $null "DELETE team member"
    }
}

# 6. ms-partidas CRUD Test (partidas - port 9006)
if ($servicesStatus["ms-partidas"] -eq "ONLINE") {
    Log-Output "`n>>> Starting CRUD tests for ms-partidas..." "Cyan"
    
    # 6.1 CRUD on Match (partidas)
    $body = '{"torneoId": 1, "ronda": "test_ronda_xyz", "estado": "PENDIENTE"}'
    $res = Invoke-HttpCall "ms-partidas" "POST" "http://localhost:9006/api/v1/partidas" $body "Create match test_ronda_xyz"
    $partidaId = Get-JsonField $res.ParsedJson "id"
    
    if ($partidaId) {
        Invoke-HttpCall "ms-partidas" "GET" "http://localhost:9006/api/v1/partidas/$partidaId" $null "GET match by ID"
        Verify-ItemInList "ms-partidas" "http://localhost:9006/api/v1/partidas" $partidaId "id" "ronda" "test_ronda_xyz" $true
        
        # 6.2 POST outcome result
        $resBody = '{"idEquipoGanador": 1, "puntajeEquipo1": 3, "puntajeEquipo2": 1, "duracionPartida": 1800}'
        Invoke-HttpCall "ms-partidas" "POST" "http://localhost:9006/api/v1/partidas/$partidaId/resultado" $resBody "Post match outcome result for match $partidaId"

        $putBody = '{"torneoId": 1, "ronda": "test_ronda_updated", "estado": "FINALIZADA"}'
        Invoke-HttpCall "ms-partidas" "PUT" "http://localhost:9006/api/v1/partidas/$partidaId" $putBody "Update match to test_ronda_updated"
        Verify-ItemInList "ms-partidas" "http://localhost:9006/api/v1/partidas" $partidaId "id" "ronda" "test_ronda_updated" $true
        
        Invoke-HttpCall "ms-partidas" "DELETE" "http://localhost:9006/api/v1/partidas/$partidaId" $null "DELETE match"
        Verify-ItemInList "ms-partidas" "http://localhost:9006/api/v1/partidas" $partidaId "id" $null $null $false
    }
}

# 7. ms-inscripciones CRUD Test (inscripciones - port 9007)
if ($servicesStatus["ms-inscripciones"] -eq "ONLINE") {
    Log-Output "`n>>> Starting CRUD test for ms-inscripciones (inscripciones)..." "Cyan"
    
    $body = '{"idUsuario": 999, "idTorneo": 1}'
    $res = Invoke-HttpCall "ms-inscripciones" "POST" "http://localhost:9007/api/v1/inscripciones" $body "Create registration for user 999, tournament 1"
    $inscId = Get-JsonField $res.ParsedJson "idInscripcion"
    if (-not $inscId) { $inscId = Get-JsonField $res.ParsedJson "id" }
    
    if ($inscId) {
        Invoke-HttpCall "ms-inscripciones" "GET" "http://localhost:9007/api/v1/inscripciones/$inscId" $null "GET registration by ID"
        Verify-ItemInList "ms-inscripciones" "http://localhost:9007/api/v1/inscripciones" $inscId "idInscripcion" "idUsuario" "999" $true
        
        Invoke-HttpCall "ms-inscripciones" "DELETE" "http://localhost:9007/api/v1/inscripciones/$inscId" $null "DELETE registration"
        Verify-ItemInList "ms-inscripciones" "http://localhost:9007/api/v1/inscripciones" $inscId "idInscripcion" $null $null $false
    }
}

# 8. ms-ranking CRUD Test (rankings, tipos, registros - port 9008)
if ($servicesStatus["ms-ranking"] -eq "ONLINE") {
    Log-Output "`n>>> Starting CRUD tests for ms-ranking..." "Cyan"
    
    # 8.1 Resource: rankings
    Log-Output "Testing Resource: rankings..." "Cyan"
    $body = '{"idJuego": 1, "idTipoRanking": 1, "registros": []}'
    $res = Invoke-HttpCall "ms-ranking" "POST" "http://localhost:9008/api/v1/rankings/rankings" $body "Create ranking for game 1, type 1"
    $rankId = Get-JsonField $res.ParsedJson "idRanking"
    if (-not $rankId) { $rankId = Get-JsonField $res.ParsedJson "id" }
    
    if ($rankId) {
        Invoke-HttpCall "ms-ranking" "GET" "http://localhost:9008/api/v1/rankings/rankings/$rankId" $null "GET ranking by ID"
        Verify-ItemInList "ms-ranking" "http://localhost:9008/api/v1/rankings/rankings" $rankId "idRanking" "idJuego" "1" $true
        
        $putBody = '{"idJuego": 1, "idTipoRanking": 2, "registros": []}'
        Invoke-HttpCall "ms-ranking" "PUT" "http://localhost:9008/api/v1/rankings/rankings/$rankId" $putBody "Update ranking"
        
        Invoke-HttpCall "ms-ranking" "DELETE" "http://localhost:9008/api/v1/rankings/rankings/$rankId" $null "DELETE ranking"
        Verify-ItemInList "ms-ranking" "http://localhost:9008/api/v1/rankings/rankings" $rankId "idRanking" $null $null $false
    }

    # 8.2 Resource: tipos (ranking types)
    Log-Output "Testing Resource: tipos..." "Cyan"
    $body = '{"nombreTipoRanking": "Tipo CRUD"}'
    $res = Invoke-HttpCall "ms-ranking" "POST" "http://localhost:9008/api/v1/rankings/tipos" $body "Create ranking type"
    $tipoRankId = Get-JsonField $res.ParsedJson "idTipoRanking"
    if (-not $tipoRankId) { $tipoRankId = Get-JsonField $res.ParsedJson "id" }

    if ($tipoRankId) {
        Invoke-HttpCall "ms-ranking" "GET" "http://localhost:9008/api/v1/rankings/tipos/$tipoRankId" $null "GET ranking type by ID"
        Verify-ItemInList "ms-ranking" "http://localhost:9008/api/v1/rankings/tipos" $tipoRankId "idTipoRanking" "nombreTipoRanking" "Tipo CRUD" $true

        $putBody = '{"nombreTipoRanking": "Tipo CRUD Upd"}'
        Invoke-HttpCall "ms-ranking" "PUT" "http://localhost:9008/api/v1/rankings/tipos/$tipoRankId" $putBody "Update ranking type"

        Invoke-HttpCall "ms-ranking" "DELETE" "http://localhost:9008/api/v1/rankings/tipos/$tipoRankId" $null "DELETE ranking type"
        Verify-ItemInList "ms-ranking" "http://localhost:9008/api/v1/rankings/tipos" $tipoRankId "idTipoRanking" $null $null $false
    }

    # 8.3 Resource: registros
    Log-Output "Testing Resource: registros..." "Cyan"
    Invoke-HttpCall "ms-ranking" "GET" "http://localhost:9008/api/v1/rankings/registros" $null "List ranking records"
}

# 9. ms-notificaciones CRUD Test (tipos, notificaciones, usuarios assignment - port 9009)
if ($servicesStatus["ms-notificaciones"] -eq "ONLINE") {
    Log-Output "`n>>> Starting CRUD tests for ms-notificaciones..." "Cyan"
    
    # 9.1 Test baseline /test endpoint
    Invoke-HttpCall "ms-notificaciones" "GET" "http://localhost:9009/api/v1/notificaciones/test" $null "GET notificaciones test"

    # 9.2 Resource: tipos (notification types)
    Log-Output "Testing Resource: tipos..." "Cyan"
    $body = '{"nombreTipoNotificacion": "test_tipo_xyz"}'
    $res = Invoke-HttpCall "ms-notificaciones" "POST" "http://localhost:9009/api/v1/notificaciones/tipos" $body "Create notification type test_tipo_xyz"
    $tipoId = Get-JsonField $res.ParsedJson "idTipoNotificacion"
    if (-not $tipoId) { $tipoId = Get-JsonField $res.ParsedJson "id" }
    
    if ($tipoId) {
        Verify-ItemInList "ms-notificaciones" "http://localhost:9009/api/v1/notificaciones/tipos" $tipoId "idTipoNotificacion" "nombreTipoNotificacion" "test_tipo_xyz" $true

        # 9.3 Resource: notificaciones (create actual notification)
        Log-Output "Testing Resource: notificaciones..." "Cyan"
        $notifBody = "{`"idTipoNotificacion`": $tipoId, `"mensaje`": `"Mensaje de prueba CRUD`"}"
        $notifRes = Invoke-HttpCall "ms-notificaciones" "POST" "http://localhost:9009/api/v1/notificaciones" $notifBody "Create notification message"
        $notifId = Get-JsonField $notifRes.ParsedJson "idNotificacion"
        if (-not $notifId) { $notifId = Get-JsonField $notifRes.ParsedJson "id" }

        if ($notifId) {
            # 9.4 Assign notification to user
            Log-Output "Testing Resource: usuarios (notification assignment)..." "Cyan"
            $assignBody = "{`"idUsuario`": 999, `"idNotificacion`": $notifId}"
            $assignRes = Invoke-HttpCall "ms-notificaciones" "POST" "http://localhost:9009/api/v1/notificaciones/usuarios" $assignBody "Assign notification $notifId to user 999"
            $notifUserId = Get-JsonField $assignRes.ParsedJson "idNotificacionUsuario"
            if (-not $notifUserId) { $notifUserId = Get-JsonField $assignRes.ParsedJson "id" }

            if ($notifUserId) {
                # List notifications of user
                Invoke-HttpCall "ms-notificaciones" "GET" "http://localhost:9009/api/v1/notificaciones/usuarios/999" $null "GET notifications for user 999"
                Invoke-HttpCall "ms-notificaciones" "GET" "http://localhost:9009/api/v1/notificaciones/usuarios/999/no-leidas" $null "GET unread notifications for user 999"

                # Mark as read
                Invoke-HttpCall "ms-notificaciones" "PUT" "http://localhost:9009/api/v1/notificaciones/usuarios/$notifUserId/leer" $null "Mark notification $notifUserId as read"

                # Delete assignment
                Invoke-HttpCall "ms-notificaciones" "DELETE" "http://localhost:9009/api/v1/notificaciones/usuarios/$notifUserId" $null "DELETE notification assignment"
            }
        }
    }
}

# 10. ms-estadisticas CRUD Test (equipos, players, partidas stats - port 9010)
if ($servicesStatus["ms-estadisticas"] -eq "ONLINE") {
    Log-Output "`n>>> Starting CRUD tests for ms-estadisticas..." "Cyan"
    
    # 10.1 Resource: equipos (team stats)
    Log-Output "Testing Resource: equipos (team stats)..." "Cyan"
    $body = '{"idEquipo": 1, "victoriasEquipo": 10, "derrotasEquipo": 2}'
    $res = Invoke-HttpCall "ms-estadisticas" "POST" "http://localhost:9010/api/v1/stats/equipos" $body "Create team stats"
    $teamStatId = Get-JsonField $res.ParsedJson "idEstadisticaEquipo"
    if (-not $teamStatId) { $teamStatId = Get-JsonField $res.ParsedJson "id" }

    if ($teamStatId) {
        Invoke-HttpCall "ms-estadisticas" "GET" "http://localhost:9010/api/v1/stats/equipos/$teamStatId" $null "GET team stats by ID"
        Verify-ItemInList "ms-estadisticas" "http://localhost:9010/api/v1/stats/equipos" $teamStatId "idEstadisticaEquipo" "idEquipo" "1" $true

        $putBody = '{"idEquipo": 1, "victoriasEquipo": 12, "derrotasEquipo": 2}'
        Invoke-HttpCall "ms-estadisticas" "PUT" "http://localhost:9010/api/v1/stats/equipos/$teamStatId" $putBody "Update team stats"

        Invoke-HttpCall "ms-estadisticas" "DELETE" "http://localhost:9010/api/v1/stats/equipos/$teamStatId" $null "DELETE team stats"
        Verify-ItemInList "ms-estadisticas" "http://localhost:9010/api/v1/stats/equipos" $teamStatId "idEstadisticaEquipo" $null $null $false
    }

    # 10.2 Resource: players (player stats)
    Log-Output "Testing Resource: players (player stats)..." "Cyan"
    $body = '{"idUsuario": 5, "victoriasJugador": 10, "derrotasJugador": 2}'
    $res = Invoke-HttpCall "ms-estadisticas" "POST" "http://localhost:9010/api/v1/stats/players" $body "Create player stats for user 5"
    $playStatId = Get-JsonField $res.ParsedJson "idEstadisticaJugador"
    if (-not $playStatId) { $playStatId = Get-JsonField $res.ParsedJson "id" }
    
    if ($playStatId) {
        Invoke-HttpCall "ms-estadisticas" "GET" "http://localhost:9010/api/v1/stats/players/$playStatId" $null "GET player statistics by ID"
        Verify-ItemInList "ms-estadisticas" "http://localhost:9010/api/v1/stats/players" $playStatId "idEstadisticaJugador" "idUsuario" "5" $true
        
        $putBody = '{"idUsuario": 5, "victoriasJugador": 12, "derrotasJugador": 2}'
        Invoke-HttpCall "ms-estadisticas" "PUT" "http://localhost:9010/api/v1/stats/players/$playStatId" $putBody "Update player stats to 12 victories"
        
        Invoke-HttpCall "ms-estadisticas" "DELETE" "http://localhost:9010/api/v1/stats/players/$playStatId" $null "DELETE player statistics"
        Verify-ItemInList "ms-estadisticas" "http://localhost:9010/api/v1/stats/players" $playStatId "idEstadisticaJugador" $null $null $false
    }

    # 10.3 Resource: partidas (match stats)
    Log-Output "Testing Resource: partidas (match stats)..." "Cyan"
    $body = '{"idPartida": 1, "duracion": "PT25M"}'
    $res = Invoke-HttpCall "ms-estadisticas" "POST" "http://localhost:9010/api/v1/stats/partidas" $body "Create match stats"
    $partStatId = Get-JsonField $res.ParsedJson "idEstadisticaPartida"
    if (-not $partStatId) { $partStatId = Get-JsonField $res.ParsedJson "id" }

    if ($partStatId) {
        Invoke-HttpCall "ms-estadisticas" "GET" "http://localhost:9010/api/v1/stats/partidas/$partStatId" $null "GET match stats by ID"
        Verify-ItemInList "ms-estadisticas" "http://localhost:9010/api/v1/stats/partidas" $partStatId "idEstadisticaPartida" "idPartida" "1" $true

        $putBody = '{"idPartida": 1, "duracion": "PT30M"}'
        Invoke-HttpCall "ms-estadisticas" "PUT" "http://localhost:9010/api/v1/stats/partidas/$partStatId" $putBody "Update match stats"

        Invoke-HttpCall "ms-estadisticas" "DELETE" "http://localhost:9010/api/v1/stats/partidas/$partStatId" $null "DELETE match stats"
        Verify-ItemInList "ms-estadisticas" "http://localhost:9010/api/v1/stats/partidas" $partStatId "idEstadisticaPartida" $null $null $false
    }
}


# ======================================================================
# SUMMARY REPORT
# ======================================================================
Log-Output ""
Log-Output "======================================================================" "Cyan"
Log-Output "                           SUMMARY OF RESULTS                         " "Cyan"
Log-Output "======================================================================" "Cyan"
Log-Output "Total Baseline Endpoints : $($stats.Total)" "White"
Log-Output "Successful Baseline      : $($stats.Success)" "Green"
Log-Output "Failed/Error Baseline    : $($stats.Failed)" "Red"
Log-Output "Offline Baseline         : $($stats.Offline)" "DarkRed"
Log-Output "----------------------------------------------------------------------" "Gray"
Log-Output "MICROSERVICES HEALTH STATUS:" "Cyan"
foreach ($key in ($servicesStatus.Keys | Sort-Object)) {
    $color = "Green"
    if ($servicesStatus[$key] -eq "OFFLINE") {
        $color = "Red"
    } elseif ($servicesStatus[$key] -like "*error*") {
        $color = "Yellow"
    }
    Log-Output "  * $key : $($servicesStatus[$key])" $color
}
Log-Output "======================================================================" "Cyan"
Log-Output "Report saved to: $outputFile" "Gray"
Log-Output "Test execution complete." "Cyan"
