package cl.duoc.commons.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class JwtProperties {

    @Value("${jwt.secret:9a7f3e82b1d6c5a4f3e2d1c0b9a8f7e6d5c4b3a201928374655647382910abcdef}")
    private String secret;

    // Expiration in milliseconds (default 1 day = 86400000 ms)
    @Value("${jwt.expiration:86400000}")
    private long expiration;
}
