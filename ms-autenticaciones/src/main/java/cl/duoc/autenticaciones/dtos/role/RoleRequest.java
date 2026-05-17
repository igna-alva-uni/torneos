package cl.duoc.autenticaciones.dtos.role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleRequest {
    @NotBlank
    private String nombreRol;
}