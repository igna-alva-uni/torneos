package cl.duoc.torneos.service;

import cl.duoc.torneos.dto.TorneosRequest;
import cl.duoc.torneos.dto.TorneosResponse;
import cl.duoc.torneos.exception.TorneoNotFoundException;
import cl.duoc.torneos.exception.VideojuegoDuplicadoException;
import cl.duoc.torneos.mapper.TorneosMapper;
import cl.duoc.torneos.model.Torneos;
import cl.duoc.torneos.repository.TorneosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TorneosService {
    private final TorneosRepository torneosRepository;
    private final TorneosMapper torneosMapper;

    public List<TorneosResponse> findAll() {
        return torneosMapper.toResponseList(torneosRepository.findAll());
    }

    public TorneosRequest findById(Long id) {
        Torneos torneos = (Torneos) torneosRepository.findById(id)
                .orElseThrow(() -> new TorneoNotFoundException(id));
        return torneosMapper.toResponse(torneos);
    }

    public TorneosRequest findByVideojugo(String videojuego) {
        Torneos torneos = torneosRepository.findByVideojuego(videojuego)
                .orElseThrow(() -> new TorneoNotFoundException(videojuego));
        return torneosMapper.toResponse(torneos);
    }

    public TorneosRequest create(TorneosRequest request) {
        if (torneosRepository.existsByVideojuego(request.getVideojuego())) {
            String juegoExistente = torneosRepository.findByVideojuego(request.getVideojuego())
                    .map(Torneos::getVideojuego)
                    .orElse("Desconocido");
            throw new VideojuegoDuplicadoException(request.getVideojuego(), juegoExistente);
        }

        Torneos torneos = torneosMapper.toModel(request);

        if (torneos == null) {
            throw new IllegalArgumentException("La solicitud del videojuego no pudo ser procesada.");
        }

        Torneos guardado = torneosRepository.save(torneos);
        return torneosMapper.toResponse(guardado);

    }

    public TorneosRequest update(Long id, TorneosRequest request){
        Torneos existente = (Torneos) torneosRepository.findById(id)
                .orElseThrow(()-> new TorneoNotFoundException(id));

        if (!existente.getVideojuego().equalsIgnoreCase(request.getVideojuego())){
            if (torneosRepository.existsByVideojuego(request.getVideojuego())) {
                torneosRepository.findByVideojuego(request.getVideojuego()).ifPresent(torneos ->{
                    throw new VideojuegoDuplicadoException(request.getVideojuego(), existente.getVideojuego());
                });
            }
        }

        existente.setNom_torneo(request.getNom_torneo());
        existente.setVideojuego(request.getVideojuego());
        existente.setPremio(request.getPremio());
        existente.setFormato(request.getFormato());

        Torneos guardado = torneosRepository.save(existente);
        return torneosMapper.toResponse(guardado);
    }

    public void deleteById(Long id){
        torneosRepository.findById(id).orElseThrow(()-> new TorneoNotFoundException(id));
        torneosRepository.deleteById(id);
    }
}