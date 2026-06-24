package cl.duoc.notificaciones.dto;

import lombok.Data;

@Data
public class NotificacionUsuarioRequestDTO {
    private Integer idUsuario;
    private Integer idNotificacion;
    private Boolean leida;
}