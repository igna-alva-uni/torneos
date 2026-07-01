package cl.duoc.partidas.dto;

import cl.duoc.partidas.model.EstadoPartida;
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
public class PartidaResponse extends RepresentationModel<PartidaResponse> {
    private Integer id;
    private Integer torneoId;
    private String ronda;
    private EstadoPartida estado;
}
