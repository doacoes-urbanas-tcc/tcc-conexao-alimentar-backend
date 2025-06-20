package tcc.conexao_alimentar.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutorRuralCadastroDTO {

    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private EnderecoDTO endereco;
    private String numeroRegistroRural;

}
