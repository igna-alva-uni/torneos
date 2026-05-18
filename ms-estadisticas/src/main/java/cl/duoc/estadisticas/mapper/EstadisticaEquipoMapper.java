package cl.duoc.estadisticas.mapper;

import cl.duoc.estadisticas.dto.EstadisticaRequest;
import cl.duoc.estadisticas.dto.EstadisticaResponse;
import cl.duoc.estadisticas.model.EstadisticaEquipo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.time.Duration;

@Mapper(componentModel = "spring")
public interface EstadisticaEquipoMapper {

    @Mapping(target = "id", source = "idEstadisticaEquipo") 
    @Mapping(target = "idReferencia", source = "idEquipo") 
    @Mapping(target = "tipo", constant = "EQUIPO")
    @Mapping(target = "victorias", source = "victoriasEquipo") 
    @Mapping(target = "derrotas", source = "derrotasEquipo") 
    @Mapping(target = "porcentajeExito", expression = "java(calcularWinRate(equipo.getVictoriasEquipo(), equipo.getDerrotasEquipo()))")
    @Mapping(target = "tiempoTotal", ignore = true)
    @Mapping(target = "segundos", ignore = true)
    EstadisticaResponse toResponse(EstadisticaEquipo equipo);

    @Mapping(target = "idEstadisticaEquipo", ignore = true)
    @Mapping(target = "idEquipo", source = "idEquipo") 
    @Mapping(target = "victoriasEquipo", source = "victorias")
    @Mapping(target = "derrotasEquipo", source = "derrotas")
    EstadisticaEquipo toEquipoModel(EstadisticaRequest request);

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
