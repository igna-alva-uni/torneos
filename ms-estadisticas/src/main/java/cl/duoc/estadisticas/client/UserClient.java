package cl.duoc.estadisticas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuarios")
public interface UserClient {

    @GetMapping("/api/v1/usuarios/usuarios/{id}")
    Object getUsuarioById(@PathVariable("id") Long id);
}
