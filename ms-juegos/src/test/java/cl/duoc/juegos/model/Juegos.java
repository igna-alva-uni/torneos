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
    private String catalogo;
    private String genero;
    private String plataforma;
}
