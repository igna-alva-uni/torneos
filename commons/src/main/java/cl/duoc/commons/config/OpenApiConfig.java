package cl.duoc.commons.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import java.util.Collections;
import java.util.Map;

@Configuration
public class OpenApiConfig {

    @Bean
    public static BeanFactoryPostProcessor springdocPathCustomizer(Environment environment) {
        return beanFactory -> {
            if (environment instanceof ConfigurableEnvironment) {
                ConfigurableEnvironment env = (ConfigurableEnvironment) environment;
                String appName = env.getProperty("spring.application.name");
                if (appName != null && appName.startsWith("ms-")) {
                    String servicePrefix = appName.substring(3); // e.g. "usuarios", "autenticaciones"
                    String path = "/api/v1/" + servicePrefix + "/v3/api-docs";
                    Map<String, Object> map = Collections.singletonMap("springdoc.api-docs.path", path);
                    env.getPropertySources().addFirst(new MapPropertySource("dynamicSpringdocPath", map));
                }
            }
        };
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("BearerSec"))
                .components(new Components()
                        .addSecuritySchemes("BearerSec",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization")));
    }
}

