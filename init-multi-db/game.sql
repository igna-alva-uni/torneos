-- 1. Conectarse a la base de datos específica para este microservicio
-- \c game_service

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS plataformas_juegos CASCADE;
DROP TABLE IF EXISTS juegos CASCADE;
DROP TABLE IF EXISTS plataformas CASCADE;
DROP TABLE IF EXISTS generos CASCADE;

-- 3. Crear las tablas y sus relaciones siguiendo fielmente el documento

-- Tabla: generos [cite: 103]
CREATE TABLE generos (
    id_genero SERIAL PRIMARY KEY, -- [cite: 104]
    nombre_genero VARCHAR(50) UNIQUE NOT NULL -- [cite: 105]
);

-- Tabla: plataformas [cite: 106]
CREATE TABLE plataformas (
    id_plataforma SERIAL PRIMARY KEY, -- [cite: 107]
    nombre_plataforma VARCHAR(50) UNIQUE NOT NULL -- [cite: 108]
);

-- Tabla: juegos [cite: 98]
CREATE TABLE juegos (
    id_juego SERIAL PRIMARY KEY, -- [cite: 99]
    nombre_juego VARCHAR(100) NOT NULL, -- [cite: 100]
    id_genero INT NOT NULL, -- [cite: 101]
    descripcion TEXT, -- [cite: 102]
    CONSTRAINT fk_juego_genero FOREIGN KEY (id_genero) REFERENCES generos(id_genero) ON DELETE RESTRICT
);

-- Tabla: plataformas_juegos [cite: 109]
-- Esta tabla permite la relación muchos a muchos entre juegos y plataformas
CREATE TABLE plataformas_juegos (
    id_juego INT NOT NULL, -- [cite: 110]
    id_plataforma INT NOT NULL, -- [cite: 111]
    PRIMARY KEY (id_juego, id_plataforma),
    CONSTRAINT fk_pj_juego FOREIGN KEY (id_juego) REFERENCES juegos(id_juego) ON DELETE CASCADE,
    CONSTRAINT fk_pj_plataforma FOREIGN KEY (id_plataforma) REFERENCES plataformas(id_plataforma) ON DELETE CASCADE
);

-- 4. Poblar las tablas con datos de prueba coherentes y reales

-- Insertar Géneros
INSERT INTO generos (nombre_genero) VALUES 
('MOBA'),
('FPS'),
('Battle Royale'),
('Fighting'),
('Sports');

-- Insertar Plataformas
INSERT INTO plataformas (nombre_plataforma) VALUES 
('PC'),
('PlayStation 5'),
('Xbox Series X'),
('Nintendo Switch'),
('Mobile');

-- Insertar Juegos
INSERT INTO juegos (nombre_juego, id_genero, descripcion) VALUES 
('League of Legends', 1, 'Arena de batalla multijugador en línea'),
('Valorant', 2, 'Shooter táctico de 5 vs 5 con habilidades'),
('Counter-Strike 2', 2, 'El shooter competitivo por excelencia'),
('Rocket League', 5, 'Fútbol con autos propulsados por cohetes'),
('Street Fighter 6', 4, 'Juego de lucha uno contra uno');

-- Insertar Relaciones Plataformas_Juegos (Casos de uso reales)
INSERT INTO plataformas_juegos (id_juego, id_plataforma) VALUES 
(1, 1), -- LoL en PC
(2, 1), -- Valorant en PC
(3, 1), -- CS2 en PC
(4, 1), (4, 2), (4, 3), (4, 4), -- Rocket League es multiplataforma
(5, 1), (5, 2), (5, 3); -- SF6 en PC, PS5 y Xbox