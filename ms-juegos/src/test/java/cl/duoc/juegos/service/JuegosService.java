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

    public JuegosResponse create(JuegosRequest request){
        if (juegosRepository.existsByGenero(request.getGenero())){
            String catalogoExistente = juegosRepository.findByGenero(request.getGenero())
            .map(Juegos::getCatalogo)
            .orElse("Desconocido");
            throw new GeneroDuplicadoException(request.getGenero(), catalogoExistente);
        }

        Juegos juegos = juegosMapper.toModel(request);

        if (juegos == null){
            throw new IllegalArgumentException("La solicitud del videojuego no pudo ser procesada.");
        }

        Juegos guardado = juegosRepository.save(juegos);
        return juegosMapper.toResponse(guardado);
    }

    
}
