package cl.duoc.commons.client;

import cl.duoc.commons.config.FeignConfig;
import cl.duoc.commons.dto.RankingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-ranking", configuration = FeignConfig.class)
public interface RankingClient {

    @GetMapping("/api/v1/rankings/rankings/{id}")
    RankingResponse getRankingById(@PathVariable("id") Integer id);
}
