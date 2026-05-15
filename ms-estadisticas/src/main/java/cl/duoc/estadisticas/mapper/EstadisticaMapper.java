package cl.duoc.estadisticas.mapper;

import cl.duoc.estadisticas.dto.EstadisticaRequest;
import cl.duoc.estadisticas.dto.EstadisticaResponse;
import cl.duoc.estadisticas.model.EstadisticaEquipo;
import cl.duoc.estadisticas.model.EstadisticaJugador;
import cl.duoc.estadisticas.model.EstadisticaPartida;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.time.Duration;

@Mapper(componentModel = "spring")
public interface EstadisticaMapper {


    @Mapping(target = "id", source = "idEstadisticaJugador") 
    @Mapping(target = "idReferencia", source = "idUsuario") 
    @Mapping(target = "tipo", constant = "JUGADOR")
    @Mapping(target = "victorias", source = "victoriasJugador") 
    @Mapping(target = "derrotas", source = "derrotasJugador") 
    @Mapping(target = "porcentajeExito", expression = "java(calcularWinRate(jugador.getVictoriasJugador(), jugador.getDerrotasJugador()))")
    @Mapping(target = "tiempoTotal", ignore = true)
    @Mapping(target = "segundos", ignore = true)
    EstadisticaResponse toResponse(EstadisticaJugador jugador);

    @Mapping(target = "id", source = "idEstadisticaEquipo") 
    @Mapping(target = "idReferencia", source = "idEquipo") 
    @Mapping(target = "tipo", constant = "EQUIPO")
    @Mapping(target = "victorias", source = "victoriasEquipo") 
    @Mapping(target = "derrotas", source = "derrotasEquipo") 
    @Mapping(target = "porcentajeExito", expression = "java(calcularWinRate(equipo.getVictoriasEquipo(), equipo.getDerrotasEquipo()))")
    @Mapping(target = "tiempoTotal", ignore = true)
    @Mapping(target = "segundos", ignore = true)
    EstadisticaResponse toResponse(EstadisticaEquipo equipo);

    @Mapping(target = "id", source = "idEstadisticaPartida") 
    @Mapping(target = "idReferencia", source = "idPartida") 
    @Mapping(target = "tipo", constant = "PARTIDA")
    @Mapping(target = "victorias", ignore = true)
    @Mapping(target = "derrotas", ignore = true)
    @Mapping(target = "porcentajeExito", ignore = true)
    @Mapping(target = "segundos", expression = "java(partida.getDuracion().getSeconds())") 
    @Mapping(target = "tiempoTotal", source = "duracion", qualifiedByName = "formatDuration") 
    EstadisticaResponse toResponse(EstadisticaPartida partida);


// --- REQUEST -> ENTITY (Mapeos de Entrada) ---

    @Mapping(target = "idEstadisticaJugador", ignore = true)
    @Mapping(target = "idUsuario", source = "idUsuario") // Cambiado: antes decía idReferencia
    @Mapping(target = "victoriasJugador", source = "victorias")
    @Mapping(target = "derrotasJugador", source = "derrotas")
    EstadisticaJugador toJugadorModel(EstadisticaRequest request);

    @Mapping(target = "idEstadisticaEquipo", ignore = true)
    @Mapping(target = "idEquipo", source = "idEquipo") // Cambiado: antes decía idReferencia
    @Mapping(target = "victoriasEquipo", source = "victorias")
    @Mapping(target = "derrotasEquipo", source = "derrotas")
    EstadisticaEquipo toEquipoModel(EstadisticaRequest request);

    @Mapping(target = "idEstadisticaPartida", ignore = true)
    @Mapping(target = "idPartida", source = "idPartida") // Cambiado: antes decía idReferencia
    @Mapping(target = "duracion", source = "duracionSegundos", qualifiedByName = "secondsToDuration")
    EstadisticaPartida toPartidaModel(EstadisticaRequest request);


    @Named("formatDuration")
    default String formatDuration(Duration duration) {
        if (duration == null) return "00:00";
        long s = duration.getSeconds();
        return String.format("%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
    }

    @Named("secondsToDuration")
    default Duration secondsToDuration(Long seconds) {
        return (seconds != null) ? Duration.ofSeconds(seconds) : Duration.ZERO;
    }

    default Double calcularWinRate(Integer victorias, Integer derrotas) {
        if (victorias == null || derrotas == null || (victorias + derrotas) == 0) return 0.0;
        return (double) victorias / (victorias + derrotas) * 100;
    }
}