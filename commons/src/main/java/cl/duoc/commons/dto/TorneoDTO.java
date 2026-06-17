package cl.duoc.commons.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TorneoDTO {
    private Integer id;
    private String nombre;
    private Integer idJuego;
    private String formato;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private String estado;
}
