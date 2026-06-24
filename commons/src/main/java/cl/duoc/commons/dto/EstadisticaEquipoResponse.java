package cl.duoc.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadisticaEquipoResponse {
    private Integer idEstadisticaEquipo;
    private Integer idEquipo;
    private Integer victoriasEquipo;
    private Integer derrotasEquipo;
}
