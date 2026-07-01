package cl.duoc.notificaciones.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotificacionResponseDTO extends RepresentationModel<NotificacionResponseDTO> {
    private Integer idNotificacion;
    private Integer idTipoNotificacion;
    private String nombreTipoNotificacion;
    private String mensaje;
}
