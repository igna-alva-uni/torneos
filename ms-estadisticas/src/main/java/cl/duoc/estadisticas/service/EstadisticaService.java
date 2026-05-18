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

    private final EstadisticaJugadorRepository estadisticaJugadorRepository;
    private final EstadisticaEquipoRepository estadisticaEquipoRepository;
    private final EstadisticaPartidaRepository estadisticaPartidaRepository;
    private final EstadisticaMapper estadisticaMapper;
    
    
    public List<EstadisticaResponse> findAll() {
        return estadisticaMapper.toResponseList(estadisticaJugadorRepository.findAll());
    }
















}