-- 1. Conectarse a la base de datos específica para este microservicio
-- \c stats_service
CREATE SCHEMA IF NOT EXISTS estadistica;
SET search_path TO estadistica;

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS estadisticas_partidas CASCADE;
DROP TABLE IF EXISTS estadisticas_equipos CASCADE;
DROP TABLE IF EXISTS estadisticas_jugadores CASCADE;

-- 3. Crear las tablas y sus relaciones siguiendo fielmente el documento

-- Tabla: estadisticas_jugadores
CREATE TABLE estadisticas_jugadores (
    id_estadistica_jugador SERIAL PRIMARY KEY, -- [cite: 198]
    id_usuario INT UNIQUE NOT NULL, --  (Validado vía REST contra USER SERVICE)
    victorias_jugador INT DEFAULT 0 CHECK (victorias_jugador >= 0), -- [cite: 200]
    derrotas_jugador INT DEFAULT 0 CHECK (derrotas_jugador >= 0) -- [cite: 201]
);

-- Tabla: estadisticas_equipos
CREATE TABLE estadisticas_equipos (
    id_estadistica_equipo SERIAL PRIMARY KEY, -- [cite: 203]
    id_equipo INT UNIQUE NOT NULL, --  (Validado vía REST contra TEAM SERVICE)
    victorias_equipo INT DEFAULT 0 CHECK (victorias_equipo >= 0), -- [cite: 205]
    derrotas_equipo INT DEFAULT 0 CHECK (derrotas_equipo >= 0) -- [cite: 206]
);

-- Tabla: estadisticas_partidas
CREATE TABLE estadisticas_partidas (
    id_estadistica_partida SERIAL PRIMARY KEY, -- [cite: 208]
    id_partida INT UNIQUE NOT NULL, --  (Validado vía REST contra MATCH SERVICE)
    duracion INTERVAL NOT NULL -- [cite: 210] (Almacena el tiempo de duración de la partida)
);

-- 4. Poblar las tablas con datos de prueba coherentes y reales

-- Insertar Estadísticas de Jugadores (Asumiendo IDs de usuarios 1 al 4)
INSERT INTO estadisticas_jugadores (id_usuario, victorias_jugador, derrotas_jugador) VALUES 
(1, 150, 45), -- properolol: Un veterano con gran winrate
(2, 89, 32),  -- lleyo
(3, 210, 10), -- faker_wannabe: Estadísticas de profesional
(4, 12, 150); -- noobmaster69: Estadísticas coherentes con su nombre

-- Insertar Estadísticas de Equipos (Asumiendo IDs de equipos 1 al 3)
INSERT INTO estadisticas_equipos (id_equipo, victorias_equipo, derrotas_equipo) VALUES 
(1, 45, 12), -- Leviatán Esports
(2, 38, 15), -- KRÜ Esports
(3, 20, 25); -- Furious Gaming

-- Insertar Estadísticas de Partidas (Asumiendo IDs de partidas 1 al 3)
INSERT INTO estadisticas_partidas (id_partida, duracion) VALUES 
(1, '00:35:20'), -- Partida de 35 minutos y 20 segundos
(2, '00:42:15'), -- Partida larga
(3, '00:15:45'); -- Partida rápida (ff o stomp)