package cl.duoc.juegos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class JuegoResponse extends RepresentationModel<JuegoResponse> {
    private Integer id;
    private String nombre;
    private String genero;
    private String descripcion;
    private Set<String> plataformas;
}
