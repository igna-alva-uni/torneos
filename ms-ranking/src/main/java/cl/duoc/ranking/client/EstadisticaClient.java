package cl.duoc.ranking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-estadisticas", url = "http://localhost:8081")
public interface EstadisticaClient {

    @GetMapping("/api/v1/statistics/teams/{idEquipo}")
    EstadisticaEquipoResponse getTeamStatsById(@PathVariable("idEquipo") Integer idEquipo);
}