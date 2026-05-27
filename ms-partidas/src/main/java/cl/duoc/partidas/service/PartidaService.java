package cl.duoc.partidas.service;

import cl.duoc.partidas.client.EquipoClient;
import cl.duoc.partidas.client.TorneosClient;
import cl.duoc.partidas.dto.PartidaRequest;
import cl.duoc.partidas.dto.PartidaResponse;
import cl.duoc.partidas.dto.ResultadoPartidaRequest;
import cl.duoc.partidas.dto.ResultadoPartidaResponse;
import cl.duoc.partidas.exception.PartidaNotFoundException;
import cl.duoc.partidas.mapper.PartidaMapper;
import cl.duoc.partidas.mapper.ResultadoPartidaMapper;
import cl.duoc.partidas.model.Partida;
import cl.duoc.partidas.model.ResultadoPartida;
import cl.duoc.partidas.repository.PartidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final PartidaMapper partidaMapper;
    private final ResultadoPartidaMapper resultadoMapper;
    private final TorneosClient torneosClient;
    private final EquipoClient equipoClient;

    public List<PartidaResponse> findAll() {
        return partidaMapper.toResponseList(
                partidaRepository.findAll()
        );
    }

    public PartidaResponse findById(Integer id) {

        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() ->
                        new PartidaNotFoundException(id));

        return partidaMapper.toResponse(partida);
    }

    public ResultadoPartidaResponse crearResultado(ResultadoPartidaRequest request) {

        try {

            Partida partida = partidaRepository.findById(request.getIdPartida())
                    .orElseThrow(() -> new RuntimeException("La partida no existe"));

            // Validar que el equipo ganador existe
            if (request.getIdEquipoGanador() != null) {
                try {
                    equipoClient.getEquipo(request.getIdEquipoGanador());
                } catch (Exception e) {
                    throw new RuntimeException("No existe el equipo ganador con id: " + request.getIdEquipoGanador());
                }
            }
            ResultadoPartida resultado = ResultadoPartida.builder()
                    .partida(partida)
                    .idEquipoGanador(request.getIdEquipoGanador())
                    .puntaje(request.getPuntaje())
                    .build();


            ResultadoPartida guardado = resultado;

            return resultadoMapper.toResponse(guardado);

        } catch (Exception e) {
            throw new RuntimeException("Error al crear resultado: " + e.getMessage());
        }
    }

    public PartidaResponse create(PartidaRequest request) {

        validarTorneo(request.getTorneoId());

        Partida partida = partidaMapper.toModel(request);

        Partida guardada = partidaRepository.save(partida);

        return partidaMapper.toResponse(guardada);
    }

    public PartidaResponse update(
            Integer id,
            PartidaRequest request) {

        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() ->
                        new PartidaNotFoundException(id));

        validarTorneo(request.getTorneoId());

        partida.setTorneoId(request.getTorneoId());
        partida.setRonda(request.getRonda());
        partida.setEstado(request.getEstado());

        Partida actualizada = partidaRepository.save(partida);

        return partidaMapper.toResponse(actualizada);
    }

    public void delete(Integer id) {

        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() ->
                        new PartidaNotFoundException(id));

        partidaRepository.delete(partida);
    }

    private void validarTorneo(Integer idTorneo) {

        try {
            torneosClient.getTorneo(idTorneo);
        } catch (Exception e) {
            throw new RuntimeException(
                    "No existe el torneo con id: " + idTorneo
            );
        }
    }
}
