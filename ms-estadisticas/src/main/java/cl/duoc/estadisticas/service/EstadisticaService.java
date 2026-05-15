package cl.duoc.estadisticas.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cl.duoc.estadisticas.dto.EstadisticaRequest;
import cl.duoc.estadisticas.dto.EstadisticaResponse;
import cl.duoc.estadisticas.mapper.EstadisticaMapper;
import cl.duoc.estadisticas.model.EstadisticaEquipo;
import cl.duoc.estadisticas.model.EstadisticaJugador;
import cl.duoc.estadisticas.model.EstadisticaPartida;
import cl.duoc.estadisticas.repository.EstadisticaEquipoRepository;
import cl.duoc.estadisticas.repository.EstadisticaJugadorRepository;
import cl.duoc.estadisticas.repository.EstadisticaPartidaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstadisticaService {

    private final EstadisticaJugadorRepository jugadorRepo;
    private final EstadisticaEquipoRepository equipoRepo;
    private final EstadisticaPartidaRepository partidaRepo;
    private final EstadisticaMapper mapper;

    // CREAR O ACTUALIZAR
    public EstadisticaResponse guardar(EstadisticaRequest request, String tipo) {
        String t = tipo.toUpperCase();

        if ("JUGADOR".equals(t)) {
            EstadisticaJugador modelo = mapper.toJugadorModel(request); 
            return mapper.toResponse(jugadorRepo.save(modelo)); 
        } 
        
        if ("EQUIPO".equals(t)) {
            EstadisticaEquipo modelo = mapper.toEquipoModel(request); 
            return mapper.toResponse(equipoRepo.save(modelo)); 
        }

        if ("PARTIDA".equals(t)) {
            EstadisticaPartida modelo = mapper.toPartidaModel(request); 
            return mapper.toResponse(partidaRepo.save(modelo)); 
        }

        throw new RuntimeException("Tipo de estadística no soportado: " + tipo);
    }

    // LISTAR TODO (Combinado)
    public List<EstadisticaResponse> obtenerTodas() {
        List<EstadisticaResponse> lista = jugadorRepo.findAll().stream()
                .map(mapper::toResponse).collect(Collectors.toList());
        
        lista.addAll(equipoRepo.findAll().stream()
                .map(mapper::toResponse).collect(Collectors.toList()));
        
        lista.addAll(partidaRepo.findAll().stream()
                .map(mapper::toResponse).collect(Collectors.toList()));
        
        return lista;
    }

    // BUSCAR POR ID ESPECÍFICO
    public EstadisticaResponse obtenerPorIdReferencia(Integer id, String tipo) {
        String t = tipo.toUpperCase();

        if ("JUGADOR".equals(t)) {
            return jugadorRepo.findByIdUsuario(id) 
                    .map(mapper::toResponse)
                    .orElseThrow(() -> new RuntimeException("No hay estadísticas para el jugador: " + id));
        }

        if ("EQUIPO".equals(t)) {
            return equipoRepo.findByIdEquipo(id) 
                    .map(mapper::toResponse)
                    .orElseThrow(() -> new RuntimeException("No hay estadísticas para el equipo: " + id));
        }

        if ("PARTIDA".equals(t)) {
            return partidaRepo.findByIdPartida(id) 
                    .map(mapper::toResponse)
                    .orElseThrow(() -> new RuntimeException("No hay estadísticas para la partida: " + id));
        }

        throw new RuntimeException("Tipo inválido");
    }
}