package cl.duoc.equipos.dtos.equipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EquipoRequest {
    @Size(min = 3, max = 100)
    @NotBlank
    private String nombreEquipo;
}