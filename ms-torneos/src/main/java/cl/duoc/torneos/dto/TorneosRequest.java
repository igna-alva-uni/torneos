package cl.duoc.torneos.dto;

import cl.duoc.torneos.model.Formato;
import cl.duoc.torneos.model.Premio;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class TorneosRequest {
    @NotBlank
    private String nombre;
    @NonNull
    private Integer idJuego;
    @NonNull
    private Integer idFormato;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
}
