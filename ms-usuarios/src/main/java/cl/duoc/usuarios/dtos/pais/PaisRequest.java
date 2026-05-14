package cl.duoc.usuarios.dtos.pais;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaisRequest {
    @Size(min = 3, max = 100)
    @NotBlank
    private String nombrePais;

    @Size(min = 2, max = 3)
    @NotBlank
    private String codigoPais;
}
