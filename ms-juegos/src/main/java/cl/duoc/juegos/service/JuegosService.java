package cl.duoc.juegos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.juegos.dto.JuegosRequest;
import cl.duoc.juegos.dto.JuegosResponse;
import cl.duoc.juegos.exception.GeneroDuplicadoException;
import cl.duoc.juegos.exception.JuegoNotFoundException;
import cl.duoc.juegos.mapper.JuegosMapper;
import cl.duoc.juegos.model.Juegos;
import cl.duoc.juegos.repository.JuegosRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class JuegosService {

    private final JuegosRepository juegosRepository;
    private final JuegosMapper juegosMapper;

    public List<JuegosResponse> findAll(){
        return juegosMapper.toResponseList(juegosRepository.findAll());
    }

    public JuegosResponse findById(int id){
        Juegos juegos = juegosRepository.findById(id)
        .orElseThrow(()-> new JuegoNotFoundException(id));
        return juegosMapper.toResponse(juegos);
    }

    public JuegosResponse findByGenero(String genero){
        Juegos juegos = juegosRepository.findByGenero(genero)
            .orElseThrow(()-> new JuegoNotFoundException(genero));
        return juegosMapper.toResponse(juegos);
    }

    public JuegosResponse create(JuegosRequest request){
        if (juegosRepository.existsByGenero(request.getNombre())){
            String nombreExistente = juegosRepository.findByGenero(request.getGenero())
            .map(Juegos::getNombre)
            .orElse("Desconocido");
            throw new GeneroDuplicadoException(request.getGenero(), nombreExistente);
        }

        Juegos juegos = juegosMapper.toModel(request);

        if (juegos == null){
            throw new IllegalArgumentException("La solicitud del videojuego no pudo ser procesada.");
        }

        Juegos guardado = juegosRepository.save(juegos);
        return juegosMapper.toResponse(guardado);
    }

    public JuegosResponse update(int id, JuegosRequest request){
        Juegos existente = juegosRepository.findById(id)
        .orElseThrow(()-> new JuegoNotFoundException(id));
        
        if (!existente.getGenero().equalsIgnoreCase(request.getGenero())){
            if (juegosRepository.existsByGenero(request.getGenero())) {
                juegosRepository.findByGenero(request.getGenero()).ifPresent(juegos ->{
                    throw new GeneroDuplicadoException(request.getGenero(), juegos.getNombre());
                });
            }
        }

        existente.setNombre(request.getNombre());
        existente.setGenero(request.getGenero());
        existente.setDescripcion(request.getDescripcion());

        Juegos guardado = juegosRepository.save(existente);
        return juegosMapper.toResponse(guardado);
    }

    public void deleteById(int id){
        juegosRepository.findById(id).orElseThrow(()-> new JuegoNotFoundException(id));
        juegosRepository.deleteById(id);
    }
}
