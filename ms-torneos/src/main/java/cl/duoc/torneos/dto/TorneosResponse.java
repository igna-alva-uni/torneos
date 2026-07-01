package cl.duoc.torneos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class TorneosResponse extends RepresentationModel<TorneosResponse> {
    private Integer id;
    private String nombre;
    private Integer idJuego;
    private String formato;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private List<PremioResponse> premios;
}
