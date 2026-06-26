package cl.duoc.commons.server;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class InstanceHeaderFilter implements Filter {

    @Autowired
    private Environment env;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (response instanceof HttpServletResponse httpResponse) {
            String port = env.getProperty("local.server.port");
            String instanceId = env.getProperty("eureka.instance.instance-id");

            if (port != null) {
                httpResponse.setHeader("X-Instance-Port", port);
            }
            if (instanceId != null) {
                httpResponse.setHeader("X-Instance-Id", instanceId);
            }
        }
        chain.doFilter(request, response);
    }
}
