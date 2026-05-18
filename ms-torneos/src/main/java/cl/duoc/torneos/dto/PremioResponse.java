package cl.duoc.torneos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PremioResponse {
    private Integer posicion;
    private String recompensa;
}
