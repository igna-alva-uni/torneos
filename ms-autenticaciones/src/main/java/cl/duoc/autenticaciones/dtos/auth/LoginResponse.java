package cl.duoc.autenticaciones.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoginResponse extends RepresentationModel<LoginResponse> {
    private String token;
    private String email;
    private List<String> roles;
    private Long idUsuario;
}
