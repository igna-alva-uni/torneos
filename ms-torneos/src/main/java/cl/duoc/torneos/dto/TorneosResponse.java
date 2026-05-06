package cl.duoc.torneos.dto;

import cl.duoc.torneos.model.Formato;
import cl.duoc.torneos.model.Premio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TorneosResponse {
    private Integer id;
    private String nombre;
    private String videojuego;
    private Formato formato;
    private Premio premio;
}
