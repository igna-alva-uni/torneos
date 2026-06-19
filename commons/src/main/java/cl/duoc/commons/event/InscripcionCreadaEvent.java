package cl.duoc.commons.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InscripcionCreadaEvent {
    private Long idInscripcion;
    private Long idUsuario;
    private Long idTorneo;
    private String mensaje;
}
