package cl.duoc.torneos.dto;

import cl.duoc.torneos.model.Formato;
import cl.duoc.torneos.model.Premio;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TorneosRequest {
    private String nombre;
    private Integer idJuego;
    private Integer idFormato;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
}
