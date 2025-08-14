package tcc.conexao_alimentar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import tcc.conexao_alimentar.enums.StatusUsuario;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.model.AdministradorModel;
import tcc.conexao_alimentar.model.EnderecoModel;
import tcc.conexao_alimentar.repository.AdministradorRepository;

@Configuration
public class DataLoader {
    
    @Value("${ADMIN_PASSWORD}") 
    private String adminPassword;
    @Bean
    public CommandLineRunner loadAdmin(AdministradorRepository administradorRepository) {
        return args -> {
            String emailAdmin = "livia.admin@gmail.com";

            boolean existe = administradorRepository.findByEmail(emailAdmin).isPresent();
            if (!existe) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String senhaCriptografada =  encoder.encode(adminPassword);

                EnderecoModel endereco = new EnderecoModel(
                    "00000-000",
                    "Rua Principal",
                    "123",
                    "Centro",
                    "São Paulo",
                    "SP",
                    null,
                    null
                );

                AdministradorModel admin = new AdministradorModel();
                admin.setNome("Lívia");
                admin.setEmail(emailAdmin);
                admin.setSenha(senhaCriptografada);
                admin.setTelefone("11999999999");
                admin.setEndereco(endereco);
                admin.setTipoUsuario(TipoUsuario.ADMIN);
                admin.setStatus(StatusUsuario.ATIVO);
                admin.setFotoUrl(null);
                admin.setJustificativaReprovacao(null);

                administradorRepository.save(admin);
                System.out.println("Admin criado com sucesso!");
            } else {
                System.out.println("Admin já existe.");
            }
        };
    }
    

}
