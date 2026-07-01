package cl.duoc.partidas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ResultadoPartidaResponse extends RepresentationModel<ResultadoPartidaResponse> {
    private Integer id;
    private Integer idPartida;
    private Integer idEquipoGanador;
    private String puntaje;
}
