package cl.duoc.estadisticas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-ranking") 
public interface UsuarioClient {
    @GetMapping("/api/v1/rankings/rankings/{id}")
    RankingResponse getRankingById(@PathVariable("id") Integer id);
}

