package cl.duoc.commons.security;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {

    // Map of Token -> Expiration Timestamp in milliseconds
    private final Map<String, Long> blacklist = new ConcurrentHashMap<>();

    public void blacklistToken(String token, long expirationTimeMs) {
        blacklist.put(token, expirationTimeMs);
    }

    public boolean isBlacklisted(String token) {
        Long exp = blacklist.get(token);
        if (exp == null) {
            return false;
        }
        if (exp < System.currentTimeMillis()) {
            blacklist.remove(token); // Cleanup expired token from memory
            return false;
        }
        return true;
    }
}
