package tcc.conexao_alimentar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "tcc.conexao_alimentar.model")
@SpringBootApplication
public class ConexaoAlimentarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConexaoAlimentarApplication.class, args);
	}

}
