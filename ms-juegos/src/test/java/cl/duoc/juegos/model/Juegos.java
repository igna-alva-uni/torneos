package cl.duoc.juegos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Juegos {
    private Integer id;
    private String nombre;
    private String genero;
    private String descripcion;
}
