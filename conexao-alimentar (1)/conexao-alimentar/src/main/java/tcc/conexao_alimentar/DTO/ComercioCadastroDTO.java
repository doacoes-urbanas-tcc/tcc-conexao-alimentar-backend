package tcc.conexao_alimentar.DTO;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tcc.conexao_alimentar.enums.TipoComercio;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ComercioCadastroDTO {

    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private EnderecoDTO endereco;
    private String cnpj;
    private String nomeFantasia;
    private TipoComercio tipoComercio;

}
