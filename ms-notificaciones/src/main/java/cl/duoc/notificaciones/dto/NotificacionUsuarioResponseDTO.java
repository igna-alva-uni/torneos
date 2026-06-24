package cl.duoc.notificaciones.dto;

import lombok.Data;

@Data
public class NotificacionUsuarioResponseDTO {
    private Integer idNotificacionUsuario;
    private Integer idUsuario;
    private Integer idNotificacion;
    private String mensaje;
    private String tipoNotificacion;
    private Boolean leida;
}