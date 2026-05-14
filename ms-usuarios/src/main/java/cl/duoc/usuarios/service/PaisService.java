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
        if (paisRepo.findByCodigo(request.getCodigoPais()) .isPresent()) {
            throw new IllegalArgumentException("un pais ya usa ese codigo");
        }
        
        if (paisRepo.findByNombre(request.getNombrePais()) .isPresent()) {
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
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return mapper.toResponse(pais);
    }

    public PaisResponse getPaisByNombre(String nombre) {
        Pais pais = paisRepo.findByNombre(nombre)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return mapper.toResponse(pais);
    }

    public PaisResponse getPaisByCodigo(String codigo) {
        Pais pais = paisRepo.findByCodigo(codigo)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return mapper.toResponse(pais);
    }

    public PaisResponse updatePais(Long id, PaisRequest request) {
        Pais pais = paisRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No existe ningún usuario con ese id"));
    
        paisRepo.findByCodigo(request.getCodigoPais())
            .filter(u -> !u.getId().equals(id))
            .ifPresent(u -> { throw new RuntimeException("EMAIL_YA_REGISTRADO"); });

        paisRepo.findByNombre(request.getNombrePais())
            .filter(u -> !u.getId().equals(id))
            .ifPresent(u -> { throw new RuntimeException("EMAIL_YA_REGISTRADO"); });

        pais.setNombrePais(request.getNombrePais());
        pais.setCodigoPais(request.getCodigoPais());

        Pais updated = paisRepo.save(pais);
        return mapper.toResponse(updated);
    }

    public void deletePais(Long id) {
        if (!paisRepo.existsById(id)) {
            throw new RuntimeException("USUARIO_NO_ENCONTRADO");
        }
        paisRepo.deleteById(id);
    }

}