package cl.duoc.juegos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JuegosRequest {
    @NotBlank(message = "El nombre del juego no puede estar vacio")
    private String nombre;
    private String genero;
    private String descripcion;
}
