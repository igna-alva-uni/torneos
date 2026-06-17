-- 1. Conectarse a la base de datos específica para este microservicio
-- \c registration_service
CREATE SCHEMA IF NOT EXISTS inscripciones;

SET search_path TO inscripciones;
-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS historiales_inscripcion CASCADE;
DROP TABLE IF EXISTS inscripciones CASCADE;
DROP TABLE IF EXISTS estados_inscripcion CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;
DROP TABLE IF EXISTS torneos CASCADE;

-- 3. Crear las tablas y sus relaciones siguiendo fielmente el documento

-- Tabla: estados_inscripcion
CREATE TABLE estados_inscripcion (
    id_estado_inscripcion SERIAL PRIMARY KEY, -- [cite: 163]
    nombre_estado VARCHAR(50) UNIQUE NOT NULL -- [cite: 164]
);

-- Tablas de Referencia
CREATE TABLE usuarios (
    id_usuario INT PRIMARY KEY
);

CREATE TABLE torneos (
    id_torneo INT PRIMARY KEY
);

-- Tabla: inscripciones
CREATE TABLE inscripciones (
    id_inscripcion SERIAL PRIMARY KEY, -- [cite: 159]
    id_usuario INT NOT NULL,
    id_torneo INT NOT NULL, -- Validado vía REST contra TOURNAMENT SERVICE [cite: 160]
    estado VARCHAR(50) NOT NULL,
    fecha_inscripcion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    -- Se añade un índice único para evitar que un usuario se inscriba dos veces al mismo torneo
    CONSTRAINT uq_usuario_torneo UNIQUE (id_usuario, id_torneo),
    CONSTRAINT fk_inscripcion_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_inscripcion_torneo FOREIGN KEY (id_torneo) REFERENCES torneos(id_torneo) ON DELETE CASCADE
);

-- Índices para optimizar la búsqueda de inscripciones por torneo o por usuario
CREATE INDEX idx_inscripcion_torneo ON inscripciones(id_torneo);
CREATE INDEX idx_inscripcion_usuario ON inscripciones(id_usuario);

-- Tabla: historiales_inscripcion
CREATE TABLE historiales_inscripcion (
    id_historial_inscripcion SERIAL PRIMARY KEY, -- [cite: 166]
    id_inscripcion INT NOT NULL, -- [cite: 167]
    id_estado_inscripcion INT NOT NULL, -- [cite: 168]
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- [cite: 169]
    CONSTRAINT fk_historial_inscripcion FOREIGN KEY (id_inscripcion) REFERENCES inscripciones(id_inscripcion) ON DELETE CASCADE,
    CONSTRAINT fk_historial_estado FOREIGN KEY (id_estado_inscripcion) REFERENCES estados_inscripcion(id_estado_inscripcion) ON DELETE RESTRICT
);

-- 4. Poblar las tablas con datos de prueba coherentes y reales

-- Insertar Estados de Inscripción
INSERT INTO estados_inscripcion (nombre_estado) VALUES 
('PENDIENTE'),
('EN_REVISION'),
('ACEPTADA'),
('RECHAZADA'),
('CANCELADA');

-- Insertar datos en Tablas de Referencia
INSERT INTO usuarios (id_usuario) VALUES (1), (2), (3);
INSERT INTO torneos (id_torneo) VALUES (1), (2);

-- Insertar Inscripciones
-- Usuario 1 y 2 se inscriben al Torneo 1 (Liga LoL)
-- Usuario 3 se inscribe al Torneo 2 (Copa Valorant)
INSERT INTO inscripciones (id_usuario, id_torneo, estado, fecha_inscripcion) VALUES 
(1, 1, 'ACEPTADA', CURRENT_TIMESTAMP - INTERVAL '1 hour'), 
(2, 1, 'RECHAZADA', CURRENT_TIMESTAMP - INTERVAL '1 hour'),
(3, 2, 'PENDIENTE', CURRENT_TIMESTAMP);

-- Insertar Historiales (Simulando el flujo de estados)
-- Flujo para Inscripción 1 (Equipo 1 en Torneo 1)
INSERT INTO historiales_inscripcion (id_inscripcion, id_estado_inscripcion, fecha_registro) VALUES 
(1, 1, CURRENT_TIMESTAMP - INTERVAL '2 days'), -- Inició como Pendiente
(1, 2, CURRENT_TIMESTAMP - INTERVAL '1 day'),  -- Pasó a En Revisión
(1, 3, CURRENT_TIMESTAMP);                      -- Finalmente Aceptada

-- Flujo para Inscripción 2 (Equipo 2 en Torneo 1)
INSERT INTO historiales_inscripcion (id_inscripcion, id_estado_inscripcion, fecha_registro) VALUES 
(2, 1, CURRENT_TIMESTAMP - INTERVAL '1 day'), -- Pendiente
(2, 4, CURRENT_TIMESTAMP);                     -- Rechazada (Quizás por falta de requisitos)

-- Flujo para Inscripción 3 (Equipo 3 en Torneo 2)
INSERT INTO historiales_inscripcion (id_inscripcion, id_estado_inscripcion, fecha_registro) VALUES 
(3, 1, CURRENT_TIMESTAMP); -- Recién inscrita como Pendiente