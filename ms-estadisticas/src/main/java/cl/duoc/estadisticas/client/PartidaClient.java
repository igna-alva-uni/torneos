package cl.duoc.estadisticas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-partidas")
public interface PartidaClient {

    @GetMapping("/api/v1/partidas/{id}")
    Object getPartidaById(@PathVariable("id") Integer id);
}
