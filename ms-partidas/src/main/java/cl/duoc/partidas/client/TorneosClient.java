package cl.duoc.partidas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-torneos")
public interface TorneosClient {

    @GetMapping("/api/v1/torneos/{id}")
    TorneoDTO getTorneo(@PathVariable Integer id);
}
