package tcc.conexao_alimentar.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcc.conexao_alimentar.enums.TipoComercio;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComercioRequestDTO {

    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private EnderecoDTO endereco;
    private String cnpj;
    private String nomeFantasia;
    private TipoComercio tipoComercio;

}
