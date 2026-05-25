package cl.duoc.torneos.service;

import cl.duoc.torneos.client.JuegosClient;
import cl.duoc.torneos.dto.TorneosRequest;
import cl.duoc.torneos.dto.TorneosResponse;
import cl.duoc.torneos.exception.TorneosDuplicadoException;
import cl.duoc.torneos.exception.TorneosNotFoundException;
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
    private final JuegosClient  juegosClient;

    public List<TorneosResponse> findAll(){
        List<Torneos> torneos = torneosRepository.findAll();
        if (torneos.isEmpty()){
            throw new TorneosNotFoundException();
        }
        return torneosMapper.toResponseList(torneosRepository.findAll());
    }

    public TorneosResponse findById(int id){
        Torneos torneos = torneosRepository.findById(id)
                .orElseThrow(()-> new TorneosNotFoundException(id));
        return torneosMapper.toResponse(torneos);
    }

    public List<TorneosResponse> findByJuego(Integer idJuego){
        List<Torneos> torneos = torneosRepository.findByIdJuego(idJuego);
        if (torneos.isEmpty()){
            throw new TorneosNotFoundException(idJuego);
        }
        return torneosMapper.toResponseList(torneos);
    }
    public TorneosResponse create(TorneosRequest request){
        try {
            juegosClient.getJuegos(request.getIdJuego());
        } catch (Exception e) {
            throw new RuntimeException("El juego no existe");
        }

        if (torneosRepository.existsByNombreAndIdJuego(
                request.getNombre(),
                request.getIdJuego())){
            throw  new TorneosDuplicadoException(
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

    public TorneosResponse update(int id, TorneosRequest request){
        Torneos existente = torneosRepository.findById(id)
                .orElseThrow(()-> new TorneosNotFoundException(id));

        Formato formato = formatoRepository.findById(request.getIdFormato())
                        .orElseThrow(()-> new RuntimeException("Formato no encontrado"));

        existente.setNombre(request.getNombre());
        existente.setIdJuego(request.getIdJuego());
        existente.setFormato(formato);
        existente.setFechaInicio(request.getFechaInicio());
        existente.setFechaTermino(request.getFechaTermino());

        Torneos guardado = torneosRepository.save(existente);
        return torneosMapper.toResponse(guardado);
    }

    public void delete(Integer id) {
        Torneos torneo = torneosRepository.findById(id)
                .orElseThrow(() -> new TorneosNotFoundException(id));

        torneosRepository.delete(torneo);
    }
}
