package cl.duoc.autenticaciones.client;

import cl.duoc.commons.dto.UsuarioDTO;
import cl.duoc.commons.dto.UsuarioCreateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-usuarios")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/usuarios/{id}")
    UsuarioDTO getUsuarioById(@PathVariable("id") Long id);

    @PostMapping("/api/v1/usuarios/usuarios")
    UsuarioDTO createUsuario(@RequestBody UsuarioCreateDTO request);
}
