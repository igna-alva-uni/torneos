package cl.duoc.commons.client;

import cl.duoc.commons.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-partidas", configuration = FeignConfig.class)
public interface PartidaClient {

    @GetMapping("/api/v1/partidas/{id}")
    Object getPartidaById(@PathVariable("id") Integer id);
}
