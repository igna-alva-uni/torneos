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
public class JuegoResponse {
    private Integer id;
    private String nombre;
    private String genero;
    private String descripcion;
    private Set<String> plataformas;
}
