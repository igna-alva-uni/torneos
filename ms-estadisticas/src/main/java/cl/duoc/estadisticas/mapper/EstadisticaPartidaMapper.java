package cl.duoc.estadisticas.mapper;

import cl.duoc.estadisticas.dto.EstadisticaRequest;
import cl.duoc.estadisticas.dto.EstadisticaResponse;
import cl.duoc.estadisticas.model.EstadisticaPartida;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.time.Duration;

@Mapper(componentModel = "spring")
public interface EstadisticaPartidaMapper {

    @Mapping(target = "id", source = "idEstadisticaPartida") 
    @Mapping(target = "idReferencia", source = "idPartida") 
    @Mapping(target = "tipo", constant = "PARTIDA")
    @Mapping(target = "victorias", ignore = true)
    @Mapping(target = "derrotas", ignore = true)
    @Mapping(target = "porcentajeExito", ignore = true)
    @Mapping(target = "segundos", expression = "java(partida.getDuracion().getSeconds())") 
    @Mapping(target = "tiempoTotal", source = "duracion", qualifiedByName = "formatDuration") 
    EstadisticaResponse toResponse(EstadisticaPartida partida);

    @Mapping(target = "idEstadisticaPartida", ignore = true)
    @Mapping(target = "idPartida", source = "idPartida") 
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
