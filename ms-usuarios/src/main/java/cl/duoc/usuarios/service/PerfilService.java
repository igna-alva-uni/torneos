package cl.duoc.usuarios.service;

import java.util.List;
import org.springframework.stereotype.Service;

import cl.duoc.usuarios.dtos.perfil.PerfilRequest;
import cl.duoc.usuarios.dtos.perfil.PerfilResponse;
import cl.duoc.usuarios.mapper.PerfilMapper;
import cl.duoc.usuarios.model.Pais;
import cl.duoc.usuarios.model.Perfil;
import cl.duoc.usuarios.model.User;
import cl.duoc.usuarios.repository.PaisRepository;
import cl.duoc.usuarios.repository.PerfilRepository;
import cl.duoc.usuarios.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PerfilService {
    private final PerfilRepository perfilRepository;
    private final PerfilRepository perfilRepo;
    private final UserRepository userRepo;
    private final PaisRepository paisRepo;
    private final PerfilMapper mapper;

    public PerfilResponse addPerfil(PerfilRequest request) {

        if (!userRepo.findById(request.getIdUsuario()).isPresent()){
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        
        if (request.getIdPais() == null || !paisRepo.findById(request.getIdPais()).isPresent()) {
                throw new IllegalArgumentException("País no encontrado");
        }
        
        Perfil perfil = mapper.toModel(request);
        
        Perfil saved = perfilRepo.save(perfil);
        return mapper.toResponse(saved);
    }
    
    public List<PerfilResponse> getAllPerfiles() {
        return mapper.toResponseList(perfilRepo.findAll());
    }
    
    public PerfilResponse getPerfilById(Long id) {
        Perfil perfil = perfilRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return mapper.toResponse(perfil);
    }

    public PerfilResponse getPerfilByUserId(Long id) {
        Perfil perfil = perfilRepo.findByIdUsuario(id)
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return mapper.toResponse(perfil);
    }

    public PerfilResponse updatePerfil(Long id, PerfilRequest request) {
        Perfil perfil = perfilRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("No existe ningún usuario con ese id"));

        User user = userRepo.findById(request.getIdUsuario())
        .orElseThrow(() -> new IllegalArgumentException("No existe ningún usuario con ese id"));
        
        Pais pais = paisRepo.findById(request.getIdPais())
        .orElseThrow(() -> new IllegalArgumentException("País no encontrado"));

        perfil.setUsuario(user);
        perfil.setPais(pais);
        perfil.setNickname(request.getNickname());
        perfil.setUrlAvatar(request.getUrlAvatar());

        Perfil updated = perfilRepo.save(perfil);
        return mapper.toResponse(updated);
    }

    public void deletePerfil(Long id) {
        if (!perfilRepo.existsById(id)) {
            throw new RuntimeException("USUARIO_NO_ENCONTRADO");
        }
        perfilRepo.deleteById(id);
    }
}