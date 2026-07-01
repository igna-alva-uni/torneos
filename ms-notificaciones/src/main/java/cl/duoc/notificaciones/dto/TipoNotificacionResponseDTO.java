package cl.duoc.notificaciones.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = true)
public class TipoNotificacionResponseDTO extends RepresentationModel<TipoNotificacionResponseDTO> {
    private Integer idTipoNotificacion;
    private String nombreTipoNotificacion;
}
