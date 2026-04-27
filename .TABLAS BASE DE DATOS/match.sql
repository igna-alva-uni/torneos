-- 1. Conectarse a la base de datos específica para este microservicio
-- \c match_service

-- 2. Eliminación de las tablas en orden jerárquico inverso
DROP TABLE IF EXISTS resultados_partidas CASCADE;
DROP TABLE IF EXISTS jugadores_partida CASCADE;
DROP TABLE IF EXISTS partidas CASCADE;

-- 3. Crear las tablas y sus relaciones siguiendo fielmente el documento

-- Tabla: partidas
CREATE TABLE partidas (
    id_partida SERIAL PRIMARY KEY, --
    id_torneo INT NOT NULL, -- Validado vía REST contra TOURNAMENT SERVICE [cite: 144]
    ronda VARCHAR(50) NOT NULL, -- Ej: 'Fase de Grupos', 'Cuartos de Final', 'Final' [cite: 145]
    estado_partida VARCHAR(20) DEFAULT 'PENDIENTE' CHECK (estado_partida IN ('PENDIENTE', 'EN_CURSO', 'FINALIZADA', 'CANCELADA')) -- [cite: 146]
);

-- Tabla: jugadores_partida
-- Registra qué usuarios específicos están jugando por qué equipo en esta partida
CREATE TABLE jugadores_partida (
    id_jugador_partida SERIAL PRIMARY KEY, -- [cite: 148]
    id_usuario INT NOT NULL, -- Validado vía REST contra USER SERVICE [cite: 149]
    id_partida INT NOT NULL, -- [cite: 150]
    id_equipo INT NOT NULL, -- Validado vía REST contra TEAM SERVICE [cite: 151]
    CONSTRAINT fk_jugador_partida FOREIGN KEY (id_partida) REFERENCES partidas(id_partida) ON DELETE CASCADE
);

-- Índice para buscar rápidamente los jugadores de un equipo en una partida específica
CREATE INDEX idx_jp_partida_equipo ON jugadores_partida(id_partida, id_equipo);

-- Tabla: resultados_partidas
-- Almacena el resultado simple (ganador + puntaje) [cite: 65, 152]
CREATE TABLE resultados_partidas (
    id_resultado_partida SERIAL PRIMARY KEY, -- [cite: 153]
    id_partida INT UNIQUE NOT NULL, -- Relación 1:1, un resultado por partida [cite: 154]
    id_equipo_ganador INT, -- Validado vía REST contra TEAM SERVICE. Puede ser nulo si es empate (dependiendo del juego) [cite: 155]
    puntaje VARCHAR(20) NOT NULL, -- Ej: '2-0', '3-1', '16-14' [cite: 156]
    CONSTRAINT fk_resultado_partida FOREIGN KEY (id_partida) REFERENCES partidas(id_partida) ON DELETE CASCADE
);

-- 4. Poblar las tablas con datos de prueba coherentes y reales

-- Insertar Partidas
-- Asumiendo id_torneo 1 (Liga de LoL) e id_torneo 3 (CS2 Major)
INSERT INTO partidas (id_torneo, ronda, estado_partida) VALUES 
(1, 'Cuartos de Final - Bo3', 'FINALIZADA'),
(1, 'Semifinal - Bo5', 'EN_CURSO'),
(3, 'Gran Final - Bo5', 'PENDIENTE');

-- Insertar Jugadores por Partida
-- Simulación Equipo 1 vs Equipo 2 en la Partida 1
INSERT INTO jugadores_partida (id_usuario, id_partida, id_equipo) VALUES 
(1, 1, 1), -- properolol jugando para Leviatán en la partida 1
(2, 1, 1), -- lleyo jugando para Leviatán en la partida 1
(3, 1, 2), -- fake_wannabe jugando para KRÜ en la partida 1
(4, 1, 2); -- noobmaster jugando para KRÜ en la partida 1

-- Insertar Resultados de Partidas
-- Resultado para la Partida 1 (Ya finalizada)
INSERT INTO resultados_partidas (id_partida, id_equipo_ganador, puntaje) VALUES 
(1, 1, '2-1'); -- Ganó el equipo 1 (Leviatán) por 2 a 1 en el Bo3