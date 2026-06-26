package cl.duoc.commons.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.properties.SpringDocConfigProperties;//
import org.springframework.beans.factory.annotation.Autowired;//
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;//
import jakarta.annotation.PostConstruct;//

@Configuration
public class OpenApiConfig {
    //here
    @Autowired(required = false)
    private SpringDocConfigProperties springDocConfigProperties;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init() {
        if (springDocConfigProperties != null) {
            String appName = environment.getProperty("spring.application.name");
            if (appName != null && appName.startsWith("ms-")) {
                String servicePrefix = appName.substring(3); // e.g. "usuarios", "autenticaciones"
                // Match the Gateway routes naming convention
                String path = "/api/v1/" + servicePrefix + "/v3/api-docs";
                springDocConfigProperties.getApiDocs().setPath(path);
            }
        }
    }
    //to here

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
