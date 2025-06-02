package org.serratec.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;


import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${dominio.openapi.dev-url}")
    private String devUrl;
    @Value("${dominio.openapi.prod-url}")
    private String prodUrl;

    @Bean
    OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("URL do servidor de desenvolvimento");
        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("URL do servidor de produção");
        Contact contact = new Contact();
        contact.setEmail("contato@meudominio.com.br");
        contact.setName("Grupo 3 -  Serratec");
        contact.setUrl("https://www.meudominio.com.br");
        License apacheLicense = new License().name("Apache 	License")
                .url("https://www.apache.org/licenses/LICENSE-2.0");
        Info info = new Info().title("Ecommerce de Produtos Diversos").version("1.0").contact(contact)
                .description("API para venda de produtos diversos").termsOfService("https://www.meudominio.com.br/termos")
                .license(apacheLicense);
        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearerAuth");
        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer))
                .components(new Components().addSecuritySchemes("bearerAuth", bearerScheme))
                .addSecurityItem(securityRequirement);
    }
}