package cl.duoc.partidas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JugadorPartidaRequest {
    private Integer idUsuario;
    private Integer idPartida;
    private Integer idEquipo;
}
