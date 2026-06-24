package cl.duoc.juegos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JuegoRequest {
    private String nombre;
    private Integer idGenero;
    private String descripcion;
    private Set<Integer> plataformas;
}
