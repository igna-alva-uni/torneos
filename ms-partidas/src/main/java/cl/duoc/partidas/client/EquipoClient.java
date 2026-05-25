package cl.duoc.partidas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-equipos")
public interface EquipoClient {
    @GetMapping("/api/v1/equipos/{id}")
    EquipoDTO getEquipo(@PathVariable Integer id);
}
