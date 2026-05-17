package cl.duoc.usuarios.dtos.perfil;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilRequest {
    
    @NotNull
    private Long idUsuario;

    @Size(min = 6, max = 24)
    @NotBlank
    private String nickname;

    @Size(min = 10, max = 500)
    @NotBlank
    private String urlAvatar;

    @NotNull
    private Long idPais;
}