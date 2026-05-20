package cl.duoc.usuarios.event;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEliminadoEvent {
    private Long usuarioId;
    private String username;
    private LocalDateTime fechaEliminacion;
}
