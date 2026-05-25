package cl.duoc.notificaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionCreadaEvent {

    private Long idInscripcion;

    private Long idUsuario;

    private Long idTorneo;

    private String mensaje;
}