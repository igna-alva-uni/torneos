package cl.duoc.estadisticas.mapper;

import cl.duoc.estadisticas.dto.EstadisticaRequest;
import cl.duoc.estadisticas.dto.EstadisticaResponse;
import cl.duoc.estadisticas.model.EstadisticaJugador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.time.Duration;

@Mapper(componentModel = "spring")
public interface EstadisticaJugadorMapper {

    @Mapping(target = "id", source = "idEstadisticaJugador") 
    @Mapping(target = "idReferencia", source = "idUsuario") 
    @Mapping(target = "tipo", constant = "JUGADOR")
    @Mapping(target = "victorias", source = "victoriasJugador") 
    @Mapping(target = "derrotas", source = "derrotasJugador") 
    @Mapping(target = "porcentajeExito", expression = "java(calcularWinRate(jugador.getVictoriasJugador(), jugador.getDerrotasJugador()))")
    @Mapping(target = "tiempoTotal", ignore = true)
    @Mapping(target = "segundos", ignore = true)
    EstadisticaResponse toResponse(EstadisticaJugador jugador);

    @Mapping(target = "idEstadisticaJugador", ignore = true)
    @Mapping(target = "idUsuario", source = "idUsuario") 
    @Mapping(target = "victoriasJugador", source = "victorias")
    @Mapping(target = "derrotasJugador", source = "derrotas")
    EstadisticaJugador toJugadorModel(EstadisticaRequest request);



    default Double calcularWinRate(Integer victorias, Integer derrotas) {
        if (victorias == null || derrotas == null || (victorias + derrotas) == 0) return 0.0;
        return (double) victorias / (victorias + derrotas) * 100;
    }
    
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
}
