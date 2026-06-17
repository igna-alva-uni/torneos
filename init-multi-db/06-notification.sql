-- 1. Conectarse a la base de datos específica para este microservicio
-- \c notification_service
CREATE SCHEMA IF NOT EXISTS notificaciones;

SET search_path TO notificaciones;


-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS notificaciones_usuario CASCADE;
DROP TABLE IF EXISTS notificaciones CASCADE;
DROP TABLE IF EXISTS tipos_notificacion CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;

-- 3. Crear las tablas y sus relaciones siguiendo fielmente el documento

-- Tabla: tipos_notificacion
CREATE TABLE tipos_notificacion (
    id_tipo_notificacion SERIAL PRIMARY KEY, --
    nombre_tipo_notificacion VARCHAR(50) UNIQUE NOT NULL --
);

-- Tabla: notificaciones
-- Plantillas o mensajes base de las notificaciones
CREATE TABLE notificaciones (
    id_notificacion SERIAL PRIMARY KEY, --
    id_tipo_notificacion INT NOT NULL, --
    mensaje TEXT NOT NULL, --
    CONSTRAINT fk_notificacion_tipo FOREIGN KEY (id_tipo_notificacion) REFERENCES tipos_notificacion(id_tipo_notificacion) ON DELETE CASCADE
);

-- Tabla: usuarios (Tabla de Referencia)
CREATE TABLE usuarios (
    id_usuario INT PRIMARY KEY
);

-- Tabla: notificaciones_usuario
-- Bandeja de entrada individual de cada jugador
CREATE TABLE notificaciones_usuario (
    id_notificacion_usuario SERIAL PRIMARY KEY, --
    id_usuario INT NOT NULL, -- Validado vía REST contra USER SERVICE
    id_notificacion INT NOT NULL, --
    leida BOOLEAN DEFAULT FALSE, --
    CONSTRAINT fk_nu_notificacion FOREIGN KEY (id_notificacion) REFERENCES notificaciones(id_notificacion) ON DELETE CASCADE,
    CONSTRAINT fk_nu_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);

-- Índice para optimizar la carga de la bandeja de notificaciones (búsqueda por usuario y estado 'leida')
CREATE INDEX idx_notificaciones_usuario_estado ON notificaciones_usuario(id_usuario, leida);

-- 4. Poblar las tablas con datos de prueba coherentes y reales

-- Insertar Tipos de Notificación
INSERT INTO tipos_notificacion (nombre_tipo_notificacion) VALUES 
('Sistema'),
('Torneo'),
('Equipo'),
('Partida');

-- Insertar Notificaciones (Mensajes base)
INSERT INTO notificaciones (id_tipo_notificacion, mensaje) VALUES 
(1, '¡Bienvenido a la plataforma de torneos! Completa tu perfil.'), -- Notificación 1
(3, 'Has sido invitado a unirte al equipo "Leviatán Esports".'), -- Notificación 2
(2, 'Tu inscripción a la "Liga Nacional de Leyendas 2026" ha sido ACEPTADA.'), -- Notificación 3
(4, '¡Tu partida de Cuartos de Final está por comenzar en 10 minutos!'); -- Notificación 4

-- Insertar Usuarios de Referencia
INSERT INTO usuarios (id_usuario) VALUES (1), (3);

-- Insertar Notificaciones de Usuario (Buzón de los jugadores)
-- id_usuario 1 (properolol) recibe bienvenidas y alertas
INSERT INTO notificaciones_usuario (id_usuario, id_notificacion, leida) VALUES 
(1, 1, TRUE),  -- Bienvenida ya leída
(1, 3, FALSE), -- Inscripción aceptada sin leer
(1, 4, FALSE); -- Alerta de partida sin leer

-- id_usuario 3 (faker_wannabe) recibe invitación a equipo
INSERT INTO notificaciones_usuario (id_usuario, id_notificacion, leida) VALUES 
(3, 2, TRUE); -- Invitación leída