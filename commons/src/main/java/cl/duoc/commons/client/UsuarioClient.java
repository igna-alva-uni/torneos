package cl.duoc.commons.client;

import cl.duoc.commons.config.FeignConfig;
import cl.duoc.commons.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuarios", configuration = FeignConfig.class)
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/usuarios/{id}")
    UsuarioDTO getUsuarioById(@PathVariable("id") Long id);

    @GetMapping("/api/v1/usuarios/usuarios/{id}")
    UsuarioDTO getUsuario(@PathVariable("id") Integer id);
}
