package tcc.conexao_alimentar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI conexaoAlimentarOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("API Conexao Alimentar")
            .description("Documentação da API do sistema Conexão Alimentar")
            .version("v1.0"));
    }

}
