package cl.duoc.estadisticas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-equipos")
public interface TeamClient {

    @GetMapping("/api/v1/equipos/equipos/{id}")
    Object getEquipoById(@PathVariable("id") Long id);
}
