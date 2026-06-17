package cl.duoc.notificaciones.dto;

import lombok.Data;

@Data
public class NotificacionRequestDTO {
    private Integer idTipoNotificacion;
    private String mensaje;
}