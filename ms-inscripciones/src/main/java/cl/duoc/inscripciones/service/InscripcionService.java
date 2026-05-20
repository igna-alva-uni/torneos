package cl.duoc.inscripciones.service;

import cl.duoc.inscripciones.dto.InscripcionCreadaEvent;
import cl.duoc.inscripciones.dto.InscripcionRequest;
import cl.duoc.inscripciones.kafka.InscripcionProducer;
import cl.duoc.inscripciones.model.Inscripcion;
import cl.duoc.inscripciones.repository.InscripcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final InscripcionProducer inscripcionProducer;

    public Inscripcion crear(InscripcionRequest request) {

        Inscripcion inscripcion = Inscripcion.builder()
                .idUsuario(request.getIdUsuario())
                .idTorneo(request.getIdTorneo())
                .estado("PENDIENTE")
                .fechaInscripcion(LocalDateTime.now())
                .build();

        Inscripcion guardada = inscripcionRepository.save(inscripcion);

        InscripcionCreadaEvent event = InscripcionCreadaEvent.builder()
                .idInscripcion(guardada.getIdInscripcion())
                .idUsuario(guardada.getIdUsuario())
                .idTorneo(guardada.getIdTorneo())
                .mensaje("Usuario inscrito correctamente al torneo")
                .build();

        inscripcionProducer.enviarInscripcionCreada(event);

        return guardada;
    }

    public List<Inscripcion> listar() {
        return inscripcionRepository.findAll();
    }

    public Inscripcion buscarPorId(Long id) {
        return inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));
    }

    public void eliminar(Long id) {
        inscripcionRepository.deleteById(id);
    }
}