package cl.duoc.torneos.dto;

import cl.duoc.torneos.model.Formato;
import cl.duoc.torneos.model.Premio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TorneosResponse {
    private Integer id;
    private String nombre;
    private Integer idJuego;
    private Integer idFormato;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private List<PremioResponse> premios;
}
