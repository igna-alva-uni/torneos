package cl.duoc.torneos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Torneos {
    private Integer id;
    private String nom_torneo;
    private String videojuego;
    private Formato formato;
    private Premio premio;
}
