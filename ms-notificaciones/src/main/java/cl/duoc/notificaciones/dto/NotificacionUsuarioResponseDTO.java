package cl.duoc.notificaciones.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotificacionUsuarioResponseDTO extends RepresentationModel<NotificacionUsuarioResponseDTO> {
    private Integer idNotificacionUsuario;
    private Integer idUsuario;
    private Integer idNotificacion;
    private String mensaje;
    private String tipoNotificacion;
    private Boolean leida;
}
