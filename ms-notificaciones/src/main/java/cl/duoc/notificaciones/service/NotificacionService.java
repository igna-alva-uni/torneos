package cl.duoc.notificaciones.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.notificaciones.dto.NotificacionRequestDTO;
import cl.duoc.notificaciones.dto.NotificacionResponseDTO;
import cl.duoc.notificaciones.dto.NotificacionUsuarioRequestDTO;
import cl.duoc.notificaciones.dto.NotificacionUsuarioResponseDTO;
import cl.duoc.notificaciones.dto.TipoNotificacionRequestDTO;
import cl.duoc.notificaciones.dto.TipoNotificacionResponseDTO;
import cl.duoc.notificaciones.exception.NotificacionException;
import cl.duoc.notificaciones.mapper.NotificacionMapper;
import cl.duoc.notificaciones.model.Notificacion;
import cl.duoc.notificaciones.model.NotificacionUsuario;
import cl.duoc.notificaciones.model.TipoNotificacion;
import cl.duoc.notificaciones.repository.NotificacionRepository;
import cl.duoc.notificaciones.repository.NotificacionUsuarioRepository;
import cl.duoc.notificaciones.repository.TipoNotificacionRepository;

@Service
public class NotificacionService {

    private final TipoNotificacionRepository tipoRepository;
    private final NotificacionRepository notificacionRepository;
    private final NotificacionUsuarioRepository notificacionUsuarioRepository;
    private final NotificacionMapper mapper;

    public NotificacionService(
            TipoNotificacionRepository tipoRepository,
            NotificacionRepository notificacionRepository,
            NotificacionUsuarioRepository notificacionUsuarioRepository,
            NotificacionMapper mapper
    ) {
        this.tipoRepository = tipoRepository;
        this.notificacionRepository = notificacionRepository;
        this.notificacionUsuarioRepository = notificacionUsuarioRepository;
        this.mapper = mapper;
    }

    public TipoNotificacionResponseDTO crearTipo(TipoNotificacionRequestDTO dto) {
        if (dto.getNombreTipoNotificacion() == null || dto.getNombreTipoNotificacion().isBlank()) {
            throw new NotificacionException("El nombre del tipo de notificación es obligatorio");
        }

        TipoNotificacion tipo = new TipoNotificacion();
        tipo.setNombreTipoNotificacion(dto.getNombreTipoNotificacion());

        return mapper.toTipoResponse(tipoRepository.save(tipo));
    }

    public List<TipoNotificacionResponseDTO> listarTipos() {
        return tipoRepository.findAll()
                .stream()
                .map(mapper::toTipoResponse)
                .toList();
    }

    public NotificacionResponseDTO crearNotificacion(NotificacionRequestDTO dto) {
        if (dto.getMensaje() == null || dto.getMensaje().isBlank()) {
            throw new NotificacionException("El mensaje es obligatorio");
        }

        TipoNotificacion tipo = tipoRepository.findById(dto.getIdTipoNotificacion())
                .orElseThrow(() -> new NotificacionException("Tipo de notificación no encontrado"));

        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setTipoNotificacion(tipo);

        return mapper.toNotificacionResponse(notificacionRepository.save(notificacion));
    }

    public List<NotificacionResponseDTO> listarNotificaciones() {
        return notificacionRepository.findAll()
                .stream()
                .map(mapper::toNotificacionResponse)
                .toList();
    }

    public NotificacionUsuarioResponseDTO asignarNotificacionUsuario(NotificacionUsuarioRequestDTO dto) {
        if (dto.getIdUsuario() == null) {
            throw new NotificacionException("El idUsuario es obligatorio");
        }

        Notificacion notificacion = notificacionRepository.findById(dto.getIdNotificacion())
                .orElseThrow(() -> new NotificacionException("Notificación no encontrada"));

        NotificacionUsuario notificacionUsuario = new NotificacionUsuario();
        notificacionUsuario.setIdUsuario(dto.getIdUsuario());
        notificacionUsuario.setNotificacion(notificacion);
        notificacionUsuario.setLeida(dto.getLeida() != null ? dto.getLeida() : false);

        return mapper.toUsuarioResponse(notificacionUsuarioRepository.save(notificacionUsuario));
    }

    public List<NotificacionUsuarioResponseDTO> listarPorUsuario(Integer idUsuario) {
        return notificacionUsuarioRepository.findByIdUsuario(idUsuario)
                .stream()
                .map(mapper::toUsuarioResponse)
                .toList();
    }

    public List<NotificacionUsuarioResponseDTO> listarNoLeidasPorUsuario(Integer idUsuario) {
        return notificacionUsuarioRepository.findByIdUsuarioAndLeida(idUsuario, false)
                .stream()
                .map(mapper::toUsuarioResponse)
                .toList();
    }

    public NotificacionUsuarioResponseDTO marcarComoLeida(Integer idNotificacionUsuario) {
        NotificacionUsuario notificacionUsuario = notificacionUsuarioRepository.findById(idNotificacionUsuario)
                .orElseThrow(() -> new NotificacionException("Notificación de usuario no encontrada"));

        notificacionUsuario.setLeida(true);

        return mapper.toUsuarioResponse(notificacionUsuarioRepository.save(notificacionUsuario));
    }

    public void eliminarNotificacionUsuario(Integer idNotificacionUsuario) {
        if (!notificacionUsuarioRepository.existsById(idNotificacionUsuario)) {
            throw new NotificacionException("Notificación de usuario no encontrada");
        }

        notificacionUsuarioRepository.deleteById(idNotificacionUsuario);
    }
}


