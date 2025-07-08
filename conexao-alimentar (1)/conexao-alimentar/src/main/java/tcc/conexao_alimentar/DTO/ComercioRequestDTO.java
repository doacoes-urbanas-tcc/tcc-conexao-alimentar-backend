package tcc.conexao_alimentar.DTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcc.conexao_alimentar.enums.TipoComercio;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComercioRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    private String senha; 
    
    @Pattern(
        regexp = "^\\(\\d{2}\\) \\d{4,5}\\-\\d{4}$",
        message = "Telefone inválido. Use o formato (XX) XXXXX-XXXX"
    )
    private String telefone;
    private EnderecoDTO endereco;
    
    @Pattern(
        regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}\\-\\d{2}$",
        message = "CNPJ inválido. Use o formato XX.XXX.XXX/XXXX-XX"
    )
    private String cnpj;
    @NotBlank(message = "Nome fantasia é obrigatório")
    private String nomeFantasia;
    private TipoComercio tipoComercio;

}
