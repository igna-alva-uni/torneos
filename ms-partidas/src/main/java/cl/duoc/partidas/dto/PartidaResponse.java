package cl.duoc.partidas.dto;

import cl.duoc.partidas.model.EstadoPartida;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartidaResponse {
    private Integer id;
    private Integer idTorneo;
    private String ronda;
    private EstadoPartida estado;
}
