package cl.duoc.torneos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Torneos {
    private Integer id;
    private String nombre;
    private String videojuego;
    private Formato formato;
    private Premio premio;
}
