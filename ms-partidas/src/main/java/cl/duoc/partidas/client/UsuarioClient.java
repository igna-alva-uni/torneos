package cl.duoc.partidas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "/api/v1/usuarios")
public interface UsuarioClient {
    @GetMapping("/api/v1/usuarios/{id}")
    UsuarioDTO getUsuario(@PathVariable Integer id);
}
