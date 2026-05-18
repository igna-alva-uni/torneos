package cl.duoc.equipos.dtos.equipo;
import java.time.LocalDate;
import lombok.Data;

@Data
public class EquipoResponse {
    private Long id;
    private String nombreEquipo;
    private LocalDate fundadoEl;
}