package cl.duoc.equipos.dtos.miembro;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MiembroEquipoRequest {
    @NotNull
    private Long idUsuario;
    @NotNull
    private Long idEquipo;
    @NotNull
    private Long idRolEquipo;
}