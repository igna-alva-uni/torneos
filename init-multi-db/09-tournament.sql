-- 1. Conectarse a la base de datos específica para este microservicio
-- \c tournament_service
CREATE SCHEMA IF NOT EXISTS torneos;

SET search_path TO torneos;

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS premios CASCADE;
DROP TABLE IF EXISTS torneos CASCADE;
DROP TABLE IF EXISTS formatos_torneo CASCADE;

-- 3. Crear las tablas y sus relaciones siguiendo fielmente el documento

-- Tabla: formatos_torneo
CREATE TABLE formatos_torneo (
    id_formato SERIAL PRIMARY KEY, -- [cite: 114]
    tipo_formato VARCHAR(50) UNIQUE NOT NULL -- [cite: 115]
);

-- Tabla: torneos
CREATE TABLE torneos (
    id_torneo SERIAL PRIMARY KEY, -- [cite: 117]
    nombre_torneo VARCHAR(100) NOT NULL, -- [cite: 118]
    id_juego INT NOT NULL, --  Validado vía REST contra GAME SERVICE
    id_formato INT NOT NULL, -- [cite: 120]
    fecha_inicio DATE, -- [cite: 121]
    fecha_termino DATE, -- [cite: 122]
    CONSTRAINT fk_torneo_formato FOREIGN KEY (id_formato) REFERENCES formatos_torneo(id_formato) ON DELETE RESTRICT
);

-- Índice para búsquedas frecuentes de torneos por juego
CREATE INDEX idx_torneos_juego ON torneos(id_juego);

-- Tabla: premios
CREATE TABLE premios (
    id_premio SERIAL PRIMARY KEY, -- [cite: 124]
    id_torneo INT NOT NULL, -- [cite: 125]
    posicion INT NOT NULL CHECK (posicion > 0), -- [cite: 126]
    recompensa VARCHAR(255) NOT NULL, -- [cite: 127]
    CONSTRAINT fk_premio_torneo FOREIGN KEY (id_torneo) REFERENCES torneos(id_torneo) ON DELETE CASCADE
);

-- 4. Poblar las tablas con datos de prueba coherentes y reales

-- Insertar Formatos de Torneo
INSERT INTO formatos_torneo (tipo_formato) VALUES 
('Eliminación Directa (Single Elimination)'),
('Doble Eliminación (Double Elimination)'),
('Todos contra Todos (Round Robin)'),
('Sistema Suizo');

-- Insertar Torneos
-- Nota: Los id_juego asumen los IDs generados en GAME SERVICE (1: LoL, 2: Valorant, 3: CS2)
INSERT INTO torneos (nombre_torneo, id_juego, id_formato, fecha_inicio, fecha_termino) VALUES 
('Liga Nacional de Leyendas 2026', 1, 3, CURRENT_DATE + INTERVAL '10 days', CURRENT_DATE + INTERVAL '40 days'),
('Copa Relámpago Valorant', 2, 1, CURRENT_DATE + INTERVAL '5 days', CURRENT_DATE + INTERVAL '7 days'),
('CS2 Major Championship', 3, 2, CURRENT_DATE + INTERVAL '30 days', CURRENT_DATE + INTERVAL '45 days');

-- Insertar Premios (Asignados a las posiciones de los torneos)
-- Premios para el Torneo 1 (Liga Nacional de Leyendas)
INSERT INTO premios (id_torneo, posicion, recompensa) VALUES 
(1, 1, '$5000 USD + Trofeo'),
(1, 2, '$2000 USD'),
(1, 3, '$500 USD');

-- Premios para el Torneo 2 (Copa Relámpago Valorant)
INSERT INTO premios (id_torneo, posicion, recompensa) VALUES 
(2, 1, 'Periféricos Gamer (Teclado + Mouse)'),
(2, 2, 'Mousepad XL');

-- Premios para el Torneo 3 (CS2 Major)
INSERT INTO premios (id_torneo, posicion, recompensa) VALUES 
(3, 1, '$10000 USD + Cupo al Mundial'),
(3, 2, '$4000 USD'),
(3, 3, '$1000 USD');