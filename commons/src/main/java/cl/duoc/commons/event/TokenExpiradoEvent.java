package cl.duoc.commons.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenExpiradoEvent {
    private String token;
    private long expirationTimeMs;
}
