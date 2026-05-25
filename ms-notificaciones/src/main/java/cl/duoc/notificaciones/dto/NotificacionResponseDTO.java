package cl.duoc.notificaciones.dto;

import lombok.Data;

@Data
public class NotificacionResponseDTO {
    private Integer idNotificacion;
    private Integer idTipoNotificacion;
    private String nombreTipoNotificacion;
    private String mensaje;
}