package cl.duoc.usuarios.dtos.perfil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilResponse {
    private Long id;
    private Long idUsuario;
    private String nickname;
    private String urlAvatar;
    private String pais;
}