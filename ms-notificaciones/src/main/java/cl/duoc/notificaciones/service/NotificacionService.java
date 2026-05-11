package cl.duoc.notificaciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.notificaciones.dto.NotificacionDTO;
import cl.duoc.notificaciones.exception.NotificacionException;
import cl.duoc.notificaciones.mapper.NotificacionMapper;
import cl.duoc.notificaciones.model.Notificacion;
import cl.duoc.notificaciones.repository.NotificacionRepository;

import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository repo;

    @Autowired
    private NotificacionMapper mapper;

    public String enviar(NotificacionDTO dto) {

        if (dto.getMensaje() == null || dto.getUsuario() == null) {
            throw new NotificacionException("Datos incompletos");
        }

        Notificacion n = mapper.toEntity(dto);
        repo.save(n);

        return "Notificación guardada correctamente";
    }

    public List<Notificacion> listar() {
        return repo.findAll();
    }
}


