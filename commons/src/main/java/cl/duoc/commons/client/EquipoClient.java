package cl.duoc.commons.client;

import cl.duoc.commons.config.FeignConfig;
import cl.duoc.commons.dto.EquipoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-equipos", configuration = FeignConfig.class)
public interface EquipoClient {

    @GetMapping("/api/v1/equipos/equipos/{id}")
    EquipoDTO getEquipoById(@PathVariable("id") Long id);

    @GetMapping("/api/v1/equipos/equipos/{id}")
    EquipoDTO getEquipo(@PathVariable("id") Integer id);
}
