package cl.duoc.equipos.dtos.miembro;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = true)
public class MiembroEquipoResponse extends RepresentationModel<MiembroEquipoResponse> {
    private Long id;
    private Long idUsuario;
    private Long idEquipo;
    private String nombreEquipo;
    private Long idRolEquipo;
    private String nombreRolEquipo;
}
