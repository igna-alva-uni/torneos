package cl.duoc.inscripciones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InscripcionResponse extends RepresentationModel<InscripcionResponse> {
    private Long idInscripcion;
    private Long idUsuario;
    private Long idTorneo;
    private String estado;
    private LocalDateTime fechaInscripcion;
}
