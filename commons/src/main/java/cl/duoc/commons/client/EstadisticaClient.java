package cl.duoc.commons.client;

import cl.duoc.commons.config.FeignConfig;
import cl.duoc.commons.dto.EstadisticaEquipoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-estadisticas", configuration = FeignConfig.class)
public interface EstadisticaClient {

    @GetMapping("/api/v1/statistics/teams/{idEquipo}")
    EstadisticaEquipoResponse getTeamStatsById(@PathVariable("idEquipo") Integer idEquipo);
}
