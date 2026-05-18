package cl.duoc.notificaciones.mapper;

import org.springframework.stereotype.Component;

import cl.duoc.notificaciones.dto.NotificacionResponseDTO;
import cl.duoc.notificaciones.dto.NotificacionUsuarioResponseDTO;
import cl.duoc.notificaciones.dto.TipoNotificacionResponseDTO;
import cl.duoc.notificaciones.model.Notificacion;
import cl.duoc.notificaciones.model.NotificacionUsuario;
import cl.duoc.notificaciones.model.TipoNotificacion;

@Component
public class NotificacionMapper {

    public TipoNotificacionResponseDTO toTipoResponse(TipoNotificacion tipo) {
        TipoNotificacionResponseDTO dto = new TipoNotificacionResponseDTO();
        dto.setIdTipoNotificacion(tipo.getIdTipoNotificacion());
        dto.setNombreTipoNotificacion(tipo.getNombreTipoNotificacion());
        return dto;
    }

    public NotificacionResponseDTO toNotificacionResponse(Notificacion notificacion) {
        NotificacionResponseDTO dto = new NotificacionResponseDTO();
        dto.setIdNotificacion(notificacion.getIdNotificacion());
        dto.setMensaje(notificacion.getMensaje());

        if (notificacion.getTipoNotificacion() != null) {
            dto.setIdTipoNotificacion(notificacion.getTipoNotificacion().getIdTipoNotificacion());
            dto.setNombreTipoNotificacion(notificacion.getTipoNotificacion().getNombreTipoNotificacion());
        }

        return dto;
    }

    public NotificacionUsuarioResponseDTO toUsuarioResponse(NotificacionUsuario notificacionUsuario) {
        NotificacionUsuarioResponseDTO dto = new NotificacionUsuarioResponseDTO();
        dto.setIdNotificacionUsuario(notificacionUsuario.getIdNotificacionUsuario());
        dto.setIdUsuario(notificacionUsuario.getIdUsuario());
        dto.setLeida(notificacionUsuario.getLeida());

        if (notificacionUsuario.getNotificacion() != null) {
            dto.setIdNotificacion(notificacionUsuario.getNotificacion().getIdNotificacion());
            dto.setMensaje(notificacionUsuario.getNotificacion().getMensaje());

            if (notificacionUsuario.getNotificacion().getTipoNotificacion() != null) {
                dto.setTipoNotificacion(
                    notificacionUsuario.getNotificacion()
                        .getTipoNotificacion()
                        .getNombreTipoNotificacion()
                );
            }
        }

        return dto;
    }
}