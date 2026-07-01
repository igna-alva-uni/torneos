package cl.duoc.equipos.dtos.equipo;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = true)
public class EquipoResponse extends RepresentationModel<EquipoResponse> {
    private Long id;
    private String nombreEquipo;
    private LocalDate fundadoEl;
}
