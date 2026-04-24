package cl.duoc.juegos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JuegosResponse {
    private Integer id;
    private String catalogo;
    private String genero;
    private String plataforma;
}
