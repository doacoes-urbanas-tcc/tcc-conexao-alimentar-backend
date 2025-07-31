package tcc.conexao_alimentar.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcc.conexao_alimentar.enums.TipoUsuario;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OngResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cnpj;
    private String descricao;
    private EnderecoDTO endereco;
    private TipoUsuario tipoUsuario;
    private String fotoUrl;
    private String justificativaReprovacao;

}
