package cl.duoc.usuarios.dtos.perfil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PerfilResponse extends RepresentationModel<PerfilResponse> {
    private Long id;
    private Long idUsuario;
    private String nickname;
    private String urlAvatar;
    private String pais;
}
