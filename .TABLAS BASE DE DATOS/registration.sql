-- 1. Conectarse a la base de datos específica para este microservicio
-- \c registration_service

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS historiales_inscripcion CASCADE;
DROP TABLE IF EXISTS inscripciones CASCADE;
DROP TABLE IF EXISTS estados_inscripcion CASCADE;

-- 3. Crear las tablas y sus relaciones siguiendo fielmente el documento

-- Tabla: estados_inscripcion
CREATE TABLE estados_inscripcion (
    id_estado_inscripcion SERIAL PRIMARY KEY, -- [cite: 163]
    nombre_estado VARCHAR(50) UNIQUE NOT NULL -- [cite: 164]
);

-- Tabla: inscripciones
CREATE TABLE inscripciones (
    id_inscripcion SERIAL PRIMARY KEY, -- [cite: 159]
    id_torneo INT NOT NULL, -- Validado vía REST contra TOURNAMENT SERVICE [cite: 160]
    id_equipo INT NOT NULL, -- Validado vía REST contra TEAM SERVICE [cite: 161]
    -- Se añade un índice único para evitar que un equipo se inscriba dos veces al mismo torneo
    CONSTRAINT uq_equipo_torneo UNIQUE (id_equipo, id_torneo)
);

-- Índices para optimizar la búsqueda de inscripciones por torneo o por equipo
CREATE INDEX idx_inscripcion_torneo ON inscripciones(id_torneo);
CREATE INDEX idx_inscripcion_equipo ON inscripciones(id_equipo);

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

-- Insertar Inscripciones
-- Equipo 1 y 2 se inscriben al Torneo 1 (Liga LoL)
-- Equipo 3 se inscribe al Torneo 2 (Copa Valorant)
INSERT INTO inscripciones (id_torneo, id_equipo) VALUES 
(1, 1), 
(1, 2),
(2, 3);

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