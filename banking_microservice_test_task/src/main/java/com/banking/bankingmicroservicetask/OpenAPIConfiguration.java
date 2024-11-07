package com.banking.bankingmicroservicetask;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI bankingOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");

        Contact myContact = new Contact();
        myContact.setName("Alisa Martynova");
        myContact.setEmail("alicemartynova94@gmail.com");

        Info information = new Info()
                .title("Banking Microservice System API")
                .version("1.0")
                .description("This API exposes endpoints to manage bank account, transactions and limit.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
