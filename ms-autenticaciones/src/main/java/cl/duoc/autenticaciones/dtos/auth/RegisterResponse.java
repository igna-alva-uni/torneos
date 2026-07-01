package cl.duoc.autenticaciones.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

/**
 * Respuesta del endpoint /registro.
 * Devuelve el token JWT listo para usar + info del usuario creado.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegisterResponse extends RepresentationModel<RegisterResponse> {
    private Long idUsuario;
    private String username;
    private String email;
    private List<String> roles;
    private String token;
    private String mensaje;
}
