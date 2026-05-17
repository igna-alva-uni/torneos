-- 1. Conectarse a la base de datos específica para este microservicio
-- \c usuarios

CREATE SCHEMA IF NOT EXISTS users;

SET search_path TO users;

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS perfiles CASCADE;
DROP TABLE IF EXISTS paises CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;

-- 3. Crear las tablas y sus relaciones

-- Tabla: paises
-- Campos extraídos del modelo de datos para USER SERVICE
CREATE TABLE paises (
    id_pais SERIAL PRIMARY KEY, -- [cite: 94]
    nombre_pais VARCHAR(100) NOT NULL, -- [cite: 95]
    codigo_pais VARCHAR(3) UNIQUE NOT NULL -- [cite: 96]
);

-- Tabla: usuarios
-- Campos extraídos del modelo de datos para USER SERVICE
CREATE TABLE usuarios (
    id_usuario SERIAL PRIMARY KEY, -- [cite: 83]
    username VARCHAR(50) UNIQUE NOT NULL, -- [cite: 84]
    email VARCHAR(150) UNIQUE NOT NULL, -- [cite: 85]
    creado_el TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- [cite: 86]
);

-- Índice para búsqueda rápida por email y username, muy común en autenticación/búsqueda
CREATE INDEX idx_usuarios_username ON usuarios(username);
CREATE INDEX idx_usuarios_email ON usuarios(email);

-- Tabla: perfiles
-- Campos extraídos del modelo de datos para USER SERVICE
CREATE TABLE perfiles (
    id_perfil SERIAL PRIMARY KEY, -- [cite: 88]
    id_usuario INT UNIQUE NOT NULL, -- [cite: 89] Relación 1:1 con usuarios
    nickname VARCHAR(50), -- [cite: 90]
    url_avatar VARCHAR(255), -- [cite: 91]
    id_pais INT, -- [cite: 92]
    CONSTRAINT fk_perfil_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_perfil_pais FOREIGN KEY (id_pais) REFERENCES paises(id_pais) ON DELETE SET NULL
);

-- 4. Poblar las tablas con datos de prueba

-- Insertar Países
INSERT INTO paises (nombre_pais, codigo_pais) VALUES 
('Chile', 'CHL'),
('Argentina', 'ARG'),
('México', 'MEX'),
('España', 'ESP'),
('Colombia', 'COL');

-- Insertar Usuarios (Utilizando ejemplos del documento de arquitectura)
INSERT INTO usuarios (username, email, creado_el) VALUES 
('properolol', 'test@gmail.com', CURRENT_TIMESTAMP - INTERVAL '30 days'), -- [cite: 22]
('lleyo', 'testeo@gmail.com', CURRENT_TIMESTAMP - INTERVAL '15 days'), -- [cite: 25]
('faker_wannabe', 'midlane@mid.com', CURRENT_TIMESTAMP - INTERVAL '5 days'),
('noobmaster69', 'thor_hater@asgard.com', CURRENT_TIMESTAMP),
('pro_sniper', 'headshot@fps.com', CURRENT_TIMESTAMP),
('mythos', 'mythos@yahoo.com', CURRENT_TIMESTAMP -  INTERVAL '150 days'),
('si_key', 'sikey@gmail.com', CURRENT_TIMESTAMP -  INTERVAL '250 days');

-- Insertar Perfiles (Cubriendo casos con y sin avatar, con y sin país asignado)
INSERT INTO perfiles (id_usuario, nickname, url_avatar, id_pais) VALUES 
(1, 'Propero', 'https://midominio.com/avatars/properolol.png', 1), -- properolol es de Chile
(2, 'Lleyo El Boss', 'https://midominio.com/avatars/lleyo.jpg', 3), -- lleyo es de México
(3, 'Unkillable Demon', NULL, 2), -- Sin avatar, de Argentina
(4, 'NoobMaster', 'https://midominio.com/avatars/default.png', NULL), -- Sin país configurado
(5, 'SniperPro', NULL, 4); -- Sin avatar, de España