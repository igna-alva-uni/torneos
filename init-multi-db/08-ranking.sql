-- 1. Conectarse a la base de datos específica para este microservicio
-- \c ranking_service

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS registros_ranking CASCADE;
DROP TABLE IF EXISTS rankings CASCADE;
DROP TABLE IF EXISTS tipos_ranking CASCADE;

-- 3. Crear las tablas y sus relaciones siguiendo fielmente el documento

-- Tabla: tipos_ranking
CREATE TABLE tipos_ranking (
    id_tipo_ranking SERIAL PRIMARY KEY, -- [cite: 172]
    nombre_tipo_ranking VARCHAR(50) UNIQUE NOT NULL -- [cite: 173]
);

-- Tabla: rankings
CREATE TABLE rankings (
    id_ranking SERIAL PRIMARY KEY, -- [cite: 175]
    id_juego INT NOT NULL, -- Validado vía REST contra GAME SERVICE [cite: 176]
    id_tipo_ranking INT NOT NULL, -- [cite: 177]
    -- Restricción para evitar que haya dos rankings del mismo tipo para el mismo juego
    CONSTRAINT uq_juego_tipo UNIQUE (id_juego, id_tipo_ranking),
    CONSTRAINT fk_ranking_tipo FOREIGN KEY (id_tipo_ranking) REFERENCES tipos_ranking(id_tipo_ranking) ON DELETE RESTRICT
);

-- Índice para buscar los rankings de un juego específico rápidamente
CREATE INDEX idx_rankings_juego ON rankings(id_juego);

-- Tabla: registros_ranking
-- Almacena los puntos de cada equipo en un ranking específico
CREATE TABLE registros_ranking (
    id_registro_ranking SERIAL PRIMARY KEY, -- [cite: 179]
    id_ranking INT NOT NULL, -- [cite: 180]
    id_equipo INT NOT NULL, -- Validado vía REST contra TEAM SERVICE [cite: 181]
    puntos INT NOT NULL DEFAULT 0 CHECK (puntos >= 0), -- [cite: 182]
    -- Un equipo solo puede tener un registro (una posición) dentro de un mismo ranking
    CONSTRAINT uq_ranking_equipo UNIQUE (id_ranking, id_equipo),
    CONSTRAINT fk_registro_ranking FOREIGN KEY (id_ranking) REFERENCES rankings(id_ranking) ON DELETE CASCADE
);

-- Índice para ordenar la tabla de posiciones de un ranking por puntos (de mayor a menor)
CREATE INDEX idx_registros_puntos ON registros_ranking(id_ranking, puntos DESC);

-- 4. Poblar las tablas con datos de prueba coherentes y reales

-- Insertar Tipos de Ranking
INSERT INTO tipos_ranking (nombre_tipo_ranking) VALUES 
('Global'),
('Regional - LATAM'),
('Nacional - Chile'),
('Amateur');

-- Insertar Rankings (Asociando los juegos del GAME SERVICE)
-- id_juego 1: LoL, id_juego 2: Valorant, id_juego 3: CS2
INSERT INTO rankings (id_juego, id_tipo_ranking) VALUES 
(1, 1), -- Ranking Global de League of Legends
(1, 2), -- Ranking Regional LATAM de League of Legends
(2, 2), -- Ranking Regional LATAM de Valorant
(3, 1); -- Ranking Global de CS2

-- Insertar Registros de Ranking (Puntos de los equipos)
-- Registros para el Ranking Global de LoL (id_ranking = 1)
INSERT INTO registros_ranking (id_ranking, id_equipo, puntos) VALUES 
(1, 1, 2500), -- Equipo 1 (Leviatán) tiene 2500 pts
(1, 4, 2100), -- Equipo 4 (Isurus) tiene 2100 pts
(1, 2, 1850); -- Equipo 2 (KRÜ) tiene 1850 pts

-- Registros para el Ranking LATAM de Valorant (id_ranking = 3)
INSERT INTO registros_ranking (id_ranking, id_equipo, puntos) VALUES 
(3, 2, 3200), -- Equipo 2 (KRÜ) lidera en Valorant con 3200 pts
(3, 1, 2900), -- Equipo 1 (Leviatán) lo sigue con 2900 pts
(3, 3, 1500); -- Equipo 3 (Furious) con 1500 pts