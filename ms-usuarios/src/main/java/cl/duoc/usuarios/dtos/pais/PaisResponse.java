package cl.duoc.usuarios.dtos.pais;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaisResponse extends RepresentationModel<PaisResponse> {
    private Long id;
    private String nombrePais;
    private String codigoPais;
}
