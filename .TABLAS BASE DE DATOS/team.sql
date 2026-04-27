-- 1. Conectarse a la base de datos específica para este microservicio
-- \c team_service

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS miembros_equipos CASCADE;
DROP TABLE IF EXISTS roles_equipo CASCADE;
DROP TABLE IF EXISTS equipos CASCADE;

-- 3. Crear las tablas y sus relaciones siguiendo fielmente el documento

-- Tabla: equipos
CREATE TABLE equipos (
    id_equipo SERIAL PRIMARY KEY, -- [cite: 130]
    nombre_equipo VARCHAR(100) UNIQUE NOT NULL, -- [cite: 131]
    fundado_el DATE DEFAULT CURRENT_DATE -- [cite: 132]
);

-- Tabla: roles_equipo
CREATE TABLE roles_equipo (
    id_rol_equipo SERIAL PRIMARY KEY, -- [cite: 134]
    nombre_rol_equipo VARCHAR(50) UNIQUE NOT NULL -- [cite: 135]
);

-- Tabla: miembros_equipos
CREATE TABLE miembros_equipos (
    id_miembro_equipo SERIAL PRIMARY KEY, -- [cite: 137]
    id_usuario INT UNIQUE NOT NULL, -- Validado vía REST contra USER SERVICE[cite: 48]. UNIQUE por regla de negocio.
    id_equipo INT NOT NULL, -- [cite: 139]
    id_rol_equipo INT NOT NULL, -- [cite: 140]
    CONSTRAINT fk_miembro_equipo FOREIGN KEY (id_equipo) REFERENCES equipos(id_equipo) ON DELETE CASCADE,
    CONSTRAINT fk_miembro_rol FOREIGN KEY (id_rol_equipo) REFERENCES roles_equipo(id_rol_equipo) ON DELETE RESTRICT
);

-- Índice para búsquedas rápidas del roster de un equipo específico
CREATE INDEX idx_miembros_equipo_fk ON miembros_equipos(id_equipo);

-- 4. Poblar las tablas con datos de prueba coherentes y reales

-- Insertar Equipos
INSERT INTO equipos (nombre_equipo, fundado_el) VALUES 
('Leviatán Esports', '2020-11-01'),
('KRÜ Esports', '2020-10-15'),
('Furious Gaming', '2012-03-20'),
('Isurus', '2011-04-10');

-- Insertar Roles de Equipo
INSERT INTO roles_equipo (nombre_rol_equipo) VALUES 
('Capitán'),
('Jugador Titular'),
('Jugador Suplente'),
('Coach'),
('Analista');

-- Insertar Miembros de Equipos (Asignando usuarios ficticios del 1 al 5 de las pruebas anteriores)
-- Respetando la regla: 1 usuario = 1 solo equipo 
INSERT INTO miembros_equipos (id_usuario, id_equipo, id_rol_equipo) VALUES 
(1, 1, 1), -- Usuario 1 es Capitán de Leviatán
(2, 1, 2), -- Usuario 2 es Titular de Leviatán
(3, 2, 1), -- Usuario 3 es Capitán de KRÜ
(4, 2, 4), -- Usuario 4 es Coach de KRÜ
(5, 3, 2); -- Usuario 5 es Titular de Furious Gaming