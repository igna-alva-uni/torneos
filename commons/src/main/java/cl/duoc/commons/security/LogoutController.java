package cl.duoc.commons.security;

import cl.duoc.commons.event.TokenExpiradoEvent;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
public class LogoutController {

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired(required = false)
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Operation(summary = "Cerrar sesion e invalidar el token JWT")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                if (jwtTokenProvider.validarToken(token)) {
                    Date expiration = jwtTokenProvider.obtenerExpiracion(token);
                    long expTimeMs = expiration.getTime();

                    // 1. Añadir a la blacklist local
                    tokenBlacklistService.blacklistToken(token, expTimeMs);

                    // 2. Publicar a Kafka
                    if (kafkaTemplate != null) {
                        TokenExpiradoEvent event = new TokenExpiradoEvent(token, expTimeMs);
                        kafkaTemplate.send("tokens-expirados", event);
                    }

                    return ResponseEntity.ok("Sesión cerrada correctamente");
                }
            } catch (Exception e) {
                // Ignore exceptions and return OK/bad request depending on flow
            }
        }
        return ResponseEntity.badRequest().body("Token inválido o no proporcionado");
    }
}
