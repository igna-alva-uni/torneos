package cl.duoc.partidas.service;

import cl.duoc.partidas.client.TorneosClient;
import cl.duoc.partidas.dto.PartidaRequest;
import cl.duoc.partidas.dto.PartidaResponse;
import cl.duoc.partidas.exception.PartidaNotFoundException;
import cl.duoc.partidas.mapper.PartidaMapper;
import cl.duoc.partidas.model.Partida;
import cl.duoc.partidas.repository.PartidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final PartidaMapper partidaMapper;
    private final TorneosClient torneosClient;

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

    public PartidaResponse create(PartidaRequest request) {

        validarTorneo(request.getIdTorneo());

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

        validarTorneo(request.getIdTorneo());

        partida.setTorneos(request.getIdTorneo());
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
