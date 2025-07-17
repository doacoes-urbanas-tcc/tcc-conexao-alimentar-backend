package tcc.conexao_alimentar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan(basePackages = "tcc.conexao_alimentar.model")
@SpringBootApplication
@EnableScheduling
public class ConexaoAlimentarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConexaoAlimentarApplication.class, args);
	}

}
