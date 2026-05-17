package cl.duoc.usuarios.dtos.pais;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaisResponse {
    private Long id;
    private String nombrePais;
    private String codigoPais;
}