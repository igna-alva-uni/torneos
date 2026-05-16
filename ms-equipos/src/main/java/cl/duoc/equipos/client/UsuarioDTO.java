package cl.duoc.equipos.client;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String username;
    private String email;
}