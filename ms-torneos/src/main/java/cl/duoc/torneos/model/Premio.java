package cl.duoc.torneos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Premio {
    private String id;
    private Torneos torneos;
    private String posicion;
    private int recompensa;
}
