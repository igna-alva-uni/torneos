package cl.duoc.partidas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultadoPartidaResponse {
    private Integer id;
    private Integer idPartida;
    private Integer idEquipoGanador;
    private String puntaje;
}
