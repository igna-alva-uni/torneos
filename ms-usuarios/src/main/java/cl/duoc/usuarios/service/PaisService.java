package cl.duoc.usuarios.service;

import java.util.List;
import org.springframework.stereotype.Service;

import cl.duoc.usuarios.dtos.pais.PaisRequest;
import cl.duoc.usuarios.dtos.pais.PaisResponse;
import cl.duoc.usuarios.model.Pais;
import cl.duoc.usuarios.mapper.PaisMapper;
import cl.duoc.usuarios.repository.PaisRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaisService {
    private final PaisRepository paisRepo;
    private final PaisMapper mapper;

    public PaisResponse addPais(PaisRequest request) {
        if (paisRepo.findByCodigoPais(request.getCodigoPais()) .isPresent()) {
            throw new IllegalArgumentException("un pais ya usa ese codigo");
        }
        
        if (paisRepo.findByNombrePais(request.getNombrePais()) .isPresent()) {
            throw new IllegalArgumentException("un pais ya usa ese nombre");
        }
        
        Pais pais = mapper.toModel(request);
        
        Pais saved = paisRepo.save(pais);
        return mapper.toResponse(saved);
    }
    
    public List<PaisResponse> getAllPaises() {
        return mapper.toResponseList(paisRepo.findAll());
    }
    
    public PaisResponse getPaisById(Long id) {
        Pais pais = paisRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("no hay ningún pais con ese id"));
        return mapper.toResponse(pais);
    }

    public PaisResponse getPaisByNombre(String nombre) {
        Pais pais = paisRepo.findByNombrePais(nombre)
            .orElseThrow(() -> new IllegalArgumentException("no hay ningún pais con ese nombre"));
        return mapper.toResponse(pais);
    }

    public PaisResponse getPaisByCodigo(String codigo) {
        Pais pais = paisRepo.findByCodigoPais(codigo)
            .orElseThrow(() -> new IllegalArgumentException("no hay ningún pais con ese codigo"));
        return mapper.toResponse(pais);
    }

    public PaisResponse updatePais(Long id, PaisRequest request) {
        Pais pais = paisRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("no hay ningún pais con ese id"));
    
        paisRepo.findByCodigoPais(request.getCodigoPais())
            .filter(u -> !u.getId().equals(id))
            .ifPresent(u -> { throw new RuntimeException("un pais ya usa ese codigo"); });

        paisRepo.findByNombrePais(request.getNombrePais())
            .filter(u -> !u.getId().equals(id))
            .ifPresent(u -> { throw new RuntimeException("un pais ya usa ese nombre"); });

        pais.setNombrePais(request.getNombrePais());
        pais.setCodigoPais(request.getCodigoPais());

        Pais updated = paisRepo.save(pais);
        return mapper.toResponse(updated);
    }

    public void deletePais(Long id) {
        if (!paisRepo.existsById(id)) {
            throw new RuntimeException("no hay ningún pais con ese id");
        }
        paisRepo.deleteById(id);
    }

}