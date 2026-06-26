package cl.duoc.commons.security;

import cl.duoc.commons.event.TokenExpiradoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TokenExpiradoListener {

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @KafkaListener(
            topics = "tokens-expirados",
            groupId = "${spring.application.name}-logout-group",
            properties = {"spring.json.value.default.type=cl.duoc.commons.event.TokenExpiradoEvent"}
    )
    public void onTokenExpirado(TokenExpiradoEvent event) {
        if (event != null && event.getToken() != null) {
            tokenBlacklistService.blacklistToken(event.getToken(), event.getExpirationTimeMs());
        }
    }
}
