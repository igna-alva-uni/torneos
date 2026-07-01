package cl.duoc.autenticaciones.dtos.role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleResponse extends RepresentationModel<RoleResponse> {
    private Long id;
    private String nombreRol;
}
