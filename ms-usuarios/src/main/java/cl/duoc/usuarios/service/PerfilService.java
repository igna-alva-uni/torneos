package cl.duoc.usuarios.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PerfilRepository perfilRepo;
    private final UserRepository userRepo;
    private final PaisRepository paisRepo;
    private final PerfilMapper mapper;

    public PerfilResponse addPerfil(PerfilRequest request) {

        User user = userRepo.findById(request.getIdUsuario())
        .orElseThrow(() -> new IllegalArgumentException("no hay ningún usuario con ese id"));
    
        Pais pais = paisRepo.findById(request.getIdPais())
        .orElseThrow(() -> new IllegalArgumentException("no hay ningún pais con ese id"));
        
        Perfil perfil = mapper.toModel(request);
        perfil.setUsuario(user);
        perfil.setPais(pais);

        Perfil saved = perfilRepo.save(perfil);
        return mapper.toResponse(saved);
    }
    
    public List<PerfilResponse> getAllPerfiles() {
        return mapper.toResponseList(perfilRepo.findAll());
    }
    
    public PerfilResponse getPerfilById(Long id) {
        Perfil perfil = perfilRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("no hay ningún perfil con ese id"));
        return mapper.toResponse(perfil);
    }

    public PerfilResponse getPerfilByUserId(Long id) {
        Perfil perfil = perfilRepo.findByUsuarioId(id)
        .orElseThrow(() -> new IllegalArgumentException("no hay ningún perfil con ese id de usuario"));
        return mapper.toResponse(perfil);
    }

    public PerfilResponse updatePerfil(Long id, PerfilRequest request) {
        Perfil perfil = perfilRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("no hay ningún perfil con ese id"));

        User user = userRepo.findById(request.getIdUsuario())
        .orElseThrow(() -> new IllegalArgumentException("no hay ningún usuario con ese id"));
        
        Pais pais = paisRepo.findById(request.getIdPais())
        .orElseThrow(() -> new IllegalArgumentException("no hay ningún pais con ese id"));

        perfil.setUsuario(user);
        perfil.setPais(pais);
        perfil.setNickname(request.getNickname());
        perfil.setUrlAvatar(request.getUrlAvatar());

        Perfil updated = perfilRepo.save(perfil);
        return mapper.toResponse(updated);
    }

    @Transactional // 1. Asegura que se ejecute en una transacción
    public void deletePerfil(Long id) {
        Perfil perfil = perfilRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("no hay ningún perfil con ese id"));

        if (perfil.getUsuario() != null) {
            perfil.getUsuario().setPerfil(null);
        }
        
        perfilRepo.delete(perfil);
    }
}