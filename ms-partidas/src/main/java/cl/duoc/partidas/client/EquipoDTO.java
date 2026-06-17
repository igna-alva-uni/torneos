package cl.duoc.partidas.client;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EquipoDTO {
    private Long id;
    private String nombreEquipo;
    private LocalDate fundadoEl;
}
