package cl.duoc.notificaciones.mapper;

import org.springframework.stereotype.Component;
import cl.duoc.notificaciones.dto.NotificacionDTO;
import cl.duoc.notificaciones.model.Notificacion;

@Component
public class NotificacionMapper {

    public Notificacion toEntity(NotificacionDTO dto) {
        Notificacion n = new Notificacion();
        n.setMensaje(dto.getMensaje());
        n.setUsuario(dto.getUsuario());
        return n;
    }

    public NotificacionDTO toDTO(Notificacion n) {
        NotificacionDTO dto = new NotificacionDTO();
        dto.setMensaje(n.getMensaje());
        dto.setUsuario(n.getUsuario());
        return dto;
    }
}
