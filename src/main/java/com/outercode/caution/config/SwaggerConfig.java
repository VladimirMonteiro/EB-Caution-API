package com.outercode.caution.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI () {
        return new OpenAPI()
                .info(new Info()
                        .title("EB Caution system")
                        .version("1.0.0")
                        .description("API REST from manage caution of armorers of the EB")
                        .contact(new Contact().name("Vladimir Monteiro Souza de Lima")
                                .email("vladimir.monteiro021@gmail.com"))
                );
    }
}

