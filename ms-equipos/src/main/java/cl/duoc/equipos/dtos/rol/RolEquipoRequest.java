package cl.duoc.equipos.dtos.rol;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RolEquipoRequest {
    @NotBlank
    private String nombreRolEquipo;
}