-- 1. Conectarse a la base de datos específica para este microservicio
-- \c stats_service
CREATE SCHEMA IF NOT EXISTS estadisticas;
SET search_path TO estadisticas;

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS estadisticas_partidas CASCADE;
DROP TABLE IF EXISTS estadisticas_equipos CASCADE;
DROP TABLE IF EXISTS estadisticas_jugadores CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;
DROP TABLE IF EXISTS equipos CASCADE;
DROP TABLE IF EXISTS partidas CASCADE;

-- 3. Crear las tablas y sus relaciones siguiendo fielmente el documento

-- Tablas de Referencia
CREATE TABLE usuarios (
    id_usuario INT PRIMARY KEY
);

CREATE TABLE equipos (
    id_equipo INT PRIMARY KEY
);

CREATE TABLE partidas (
    id_partida INT PRIMARY KEY
);

-- Tabla: estadisticas_jugadores
CREATE TABLE estadisticas_jugadores (
    id_estadistica_jugador SERIAL PRIMARY KEY, -- [cite: 198]
    id_usuario INT UNIQUE NOT NULL, --  (Validado vía REST contra USER SERVICE)
    victorias_jugador INT DEFAULT 0 CHECK (victorias_jugador >= 0), -- [cite: 200]
    derrotas_jugador INT DEFAULT 0 CHECK (derrotas_jugador >= 0), -- [cite: 201]
    CONSTRAINT fk_est_jugador_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);

-- Tabla: estadisticas_equipos
CREATE TABLE estadisticas_equipos (
    id_estadistica_equipo SERIAL PRIMARY KEY, -- [cite: 203]
    id_equipo INT UNIQUE NOT NULL, --  (Validado vía REST contra TEAM SERVICE)
    victorias_equipo INT DEFAULT 0 CHECK (victorias_equipo >= 0), -- [cite: 205]
    derrotas_equipo INT DEFAULT 0 CHECK (derrotas_equipo >= 0), -- [cite: 206]
    CONSTRAINT fk_est_equipo_equipo FOREIGN KEY (id_equipo) REFERENCES equipos(id_equipo) ON DELETE CASCADE
);

-- Tabla: estadisticas_partidas
CREATE TABLE estadisticas_partidas (
    id_estadistica_partida SERIAL PRIMARY KEY, -- [cite: 208]
    id_partida INT UNIQUE NOT NULL, --  (Validado vía REST contra MATCH SERVICE)
    duracion INTERVAL NOT NULL, -- [cite: 210] (Almacena el tiempo de duración de la partida)
    CONSTRAINT fk_est_partida_partida FOREIGN KEY (id_partida) REFERENCES partidas(id_partida) ON DELETE CASCADE
);

-- 4. Poblar las tablas con datos de prueba coherentes y reales

-- Insertar datos en Tablas de Referencia
INSERT INTO usuarios (id_usuario) VALUES (1), (2), (3), (4);
INSERT INTO equipos (id_equipo) VALUES (1), (2), (3);
INSERT INTO partidas (id_partida) VALUES (1), (2), (3);

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