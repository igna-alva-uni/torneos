package cl.duoc.commons.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtTokenProvider.validarToken(jwt)) {
                if (!tokenBlacklistService.isBlacklisted(jwt)) {
                    String email = jwtTokenProvider.obtenerEmail(jwt);
                    String rol = jwtTokenProvider.obtenerRol(jwt);
                    Long idUsuario = jwtTokenProvider.obtenerIdUsuario(jwt);

                    List<GrantedAuthority> authorities = new ArrayList<>();
                    if (rol != null) {
                        String cleanRol = rol.trim().toUpperCase();
                        if (!cleanRol.startsWith("ROLE_")) {
                            authorities.add(new SimpleGrantedAuthority("ROLE_" + cleanRol));
                        }
                        authorities.add(new SimpleGrantedAuthority(cleanRol));
                    }

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            email, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    
                    // Almacenar el ID de usuario en los atributos de la petición para uso interno
                    request.setAttribute("authenticatedUserId", idUsuario);
                }
            }
        } catch (Exception e) {
            // No setting authentication in context in case of errors
            logger.error("No se pudo establecer la autenticación del usuario", e);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.contains("/v3/api-docs") ||
               path.contains("/swagger-ui") ||
               path.contains("/swagger-resources") ||
               path.contains("/webjars");
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
