package cl.duoc.torneos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class TorneoRequest {
    @NotBlank
    private String nombre;
    @NonNull
    private Integer idJuego;
    @NonNull
    private Integer idFormato;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
}
