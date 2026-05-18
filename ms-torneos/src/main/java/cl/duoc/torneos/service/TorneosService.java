package cl.duoc.torneos.service;

import cl.duoc.torneos.dto.TorneoRequest;
import cl.duoc.torneos.dto.TorneoResponse;
import cl.duoc.torneos.exception.TorneoDuplicadoException;
import cl.duoc.torneos.exception.TorneoNotFoundException;
import cl.duoc.torneos.mapper.TorneosMapper;
import cl.duoc.torneos.model.Formato;
import cl.duoc.torneos.model.Torneos;
import cl.duoc.torneos.repository.FormatoRepository;
import cl.duoc.torneos.repository.TorneosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TorneosService {

    private final TorneosRepository torneosRepository;
    private final FormatoRepository formatoRepository;
    private final TorneosMapper torneosMapper;

    public List<TorneoResponse> findAll(){
        List<Torneos> torneos = torneosRepository.findAll();
        if (torneos.isEmpty()){
            throw new TorneoNotFoundException();
        }
        return torneosMapper.toResponseList(torneosRepository.findAll());
    }

    public TorneoResponse findById(int id){
        Torneos torneos = torneosRepository.findById(id)
                .orElseThrow(()-> new TorneoNotFoundException(id));
        return torneosMapper.toResponse(torneos);
    }

    public List<TorneoResponse> findByJuego(Integer idJuego){
        List<Torneos> torneos = torneosRepository.findByIdJuego(idJuego);
        if (torneos.isEmpty()){
            throw new TorneoNotFoundException(idJuego);
        }
        return torneosMapper.toResponseList(torneos);
    }
    public TorneoResponse create(TorneoRequest request){
        if (torneosRepository.existsByNombreAndIdJuego(
                request.getNombre(),
                request.getIdJuego())){
            throw  new TorneoDuplicadoException(
                    request.getNombre(),
                    request.getIdJuego()
            );
        }
        Formato formato = formatoRepository.findById(request.getIdFormato())
                .orElseThrow(()-> new RuntimeException("Formato no encontrado"));
        Torneos torneos = torneosMapper.toModel(request);
        torneos.setFormato(formato);

        Torneos guardado= torneosRepository.save(torneos);
        return torneosMapper.toResponse(guardado);
    }

    public TorneoResponse update(int id, TorneoRequest request){
        Torneos existente = torneosRepository.findById(id)
                .orElseThrow(()-> new TorneoNotFoundException(id));

        Formato formato = formatoRepository.findById(request.getIdFormato())
                        .orElseThrow(()-> new RuntimeException("Formato no encontrado"));

        existente.setNombre(request.getNombre());
        existente.setIdJuego(request.getIdJuego());
        existente.setFormato(formato);
        existente.setFecha_inicio(request.getFechaInicio());
        existente.setFecha_final(request.getFechaTermino());

        Torneos guardado = torneosRepository.save(existente);
        return torneosMapper.toResponse(guardado);
    }

    public void delete(Integer id) {
        Torneos torneo = torneosRepository.findById(id)
                .orElseThrow(() -> new TorneoNotFoundException(id));

        torneosRepository.delete(torneo);
    }
}
