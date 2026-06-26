package cl.duoc.autenticaciones.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Respuesta del endpoint /registro.
 * Devuelve el token JWT listo para usar + info del usuario creado.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private Long idUsuario;
    private String username;
    private String email;
    private List<String> roles;
    private String token;
    private String mensaje;
}
