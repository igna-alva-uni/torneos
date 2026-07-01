package cl.duoc.equipos.dtos.rol;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = true)
public class RolEquipoResponse extends RepresentationModel<RolEquipoResponse> {
    private Long id;
    private String nombreRolEquipo;
}
