package cl.duoc.torneos.service;

import cl.duoc.torneos.dto.TorneosRequest;
import cl.duoc.torneos.dto.TorneosResponse;
import cl.duoc.torneos.exception.JuegoDuplicadoException;
import cl.duoc.torneos.exception.TorneoNotFoundException;
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

    public List<TorneosResponse> findAll(){
        return torneosMapper.toResponseList(torneosRepository.findAll());
    }

    public TorneosResponse findById(int id){
        Torneos torneos = torneosRepository.findById(id)
                .orElseThrow(()-> new TorneoNotFoundException(id));
        return torneosMapper.toResponse(torneos);
    }

    public TorneosResponse create(TorneosRequest request){
        if (torneosRepository.existsByJuego(request.getVideojuego())){
            String torneoExiste = torneosRepository.findByJuego(request.getVideojuego())
                    .map(Torneos::getNombre)
                    .orElseThrow(()-> new TorneoNotFoundException(request.getVideojuego()));
            throw new IllegalArgumentException("El torneo ya existe");
        }

        Torneos torneos = torneosMapper.toModel(request);

        if (torneos == null){
            throw new IllegalArgumentException("No se puedo procesar el torneo");
        }

        Torneos guardado = torneosRepository.save(torneos);
        return torneosMapper.toResponse(guardado);
    }

    public TorneosResponse update(int id, TorneosRequest request){
        Torneos existente = torneosRepository.findById(id)
                .orElseThrow(()-> new TorneoNotFoundException(id));

        if (!existente.getNombre().equalsIgnoreCase(request.getNombre())){
            if (torneosRepository.existsByJuego(request.getVideojuego())){
                torneosRepository.findByJuego(request.getVideojuego()).ifPresent(torneos->{
                    throw new JuegoDuplicadoException(request.getVideojuego(), torneos.getNombre());
                });
            }
        }

        existente.setNombre(request.getNombre());
        existente.setVideojuego(request.getVideojuego());
        existente.setFormato(request.getFormato());
        existente.setPremio(request.getPremio());

        Torneos guardado = torneosRepository.save(existente);
        return torneosMapper.toResponse(guardado);
    }

    public TorneosResponse delete(int id){
        torneosRepository.findById(id).orElseThrow(()-> new TorneoNotFoundException(id));
        torneosRepository.deleteById(id);
        return torneosMapper.toResponse(torneosRepository.findById(id).get());
    }
}
