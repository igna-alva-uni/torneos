package cl.duoc.autenticaciones.dtos.auth;

import lombok.Data;
import java.util.Set;

@Data
public class AuthResponse {
    private Long idUsuario;
    private String email;
    private Boolean bloqueada;
    private Set<String> roles;
}