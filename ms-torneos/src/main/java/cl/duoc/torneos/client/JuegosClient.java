package cl.duoc.torneos.client;

import lombok.NonNull;import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-juegos")
public interface JuegosClient<JuegosDTO> {

    @GetMapping("/api/v1/juegos/{id}")
    JuegosDTO getJuego(@PathVariable("id") Integer id);
}
