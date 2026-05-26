-- 1. Conectarse a la base de datos específica para este microservicio
-- \c auth_service

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS roles_usuarios CASCADE;
DROP TABLE IF EXISTS auth_usuarios CASCADE;
DROP TABLE IF EXISTS roles CASCADE;

-- 3. Crear las tablas y sus relaciones

-- Tabla: roles [cite: 75]
CREATE TABLE roles (
    id_rol SERIAL PRIMARY KEY, -- [cite: 76]
    nombre_rol VARCHAR(50) UNIQUE NOT NULL -- [cite: 77]
);

-- Tabla: auth_usuarios [cite: 70]
-- Nota: id_usuario no es SERIAL aquí porque la identidad central suele nacer o compartirse con USER SERVICE.
CREATE TABLE auth_usuarios (
    id_usuario INT PRIMARY KEY, -- [cite: 71]
    email VARCHAR(150) UNIQUE NOT NULL, -- [cite: 72]
    hash_contrasenia VARCHAR(255) NOT NULL, -- [cite: 73]
    bloqueada BOOLEAN DEFAULT FALSE -- [cite: 74]
);

-- Índice para acelerar el login (búsqueda por email)
CREATE INDEX idx_auth_usuarios_email ON auth_usuarios(email);

-- Tabla: roles_usuarios (Tabla intermedia para relación Muchos a Muchos) [cite: 78]
CREATE TABLE roles_usuarios (
    id_usuario INT NOT NULL, -- [cite: 79]
    id_rol INT NOT NULL, -- [cite: 80]
    PRIMARY KEY (id_usuario, id_rol),
    CONSTRAINT fk_ru_usuario FOREIGN KEY (id_usuario) REFERENCES auth_usuarios(id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_ru_rol FOREIGN KEY (id_rol) REFERENCES roles(id_rol) ON DELETE CASCADE
);

-- 4. Poblar las tablas con datos de prueba

-- Insertar Roles
INSERT INTO roles (nombre_rol) VALUES 
('ROLE_ADMIN'),
('ROLE_PLAYER'),
('ROLE_REFEREE'),
('ROLE_ORGANIZER');

-- Insertar Usuarios de Autenticación 
-- (Usamos los correos del ejemplo de tu documento para mantener coherencia [cite: 22, 24])
INSERT INTO auth_usuarios (id_usuario, email, hash_contrasenia, bloqueada) VALUES 
(1, 'test@gmail.com', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGG', FALSE), -- properolol [cite: 22]
(2, 'testeo@gmail.com', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGG', FALSE), -- lleyo [cite: 25]
(3, 'midlane@mid.com', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGG', TRUE),  -- Cuenta bloqueada por infracción
(4, 'thor_hater@asgard.com', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGG', FALSE),
(5, 'headshot@fps.com', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGG', FALSE);

-- Asignar Roles a los Usuarios
INSERT INTO roles_usuarios (id_usuario, id_rol) VALUES 
(1, 1), -- properolol es ADMIN
(1, 2), -- properolol también es PLAYER
(2, 2), -- lleyo es PLAYER
(3, 2), -- Usuario bloqueado era PLAYER
(4, 2), -- PLAYER regular
(5, 4); -- Este usuario es ORGANIZER de torneos