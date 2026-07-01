package cl.duoc.autenticaciones.dtos.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthResponse extends RepresentationModel<AuthResponse> {
    private Long idUsuario;
    private String email;
    private Boolean bloqueada;
    private Set<String> roles;
}
