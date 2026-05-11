package cl.duoc.estadisticas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EstadisticasRequest {
    @NotBlank(message = "No puede estar vacio")
    private String regitroRanking;
    private String tipoRanking;
    private String rankings;

}
