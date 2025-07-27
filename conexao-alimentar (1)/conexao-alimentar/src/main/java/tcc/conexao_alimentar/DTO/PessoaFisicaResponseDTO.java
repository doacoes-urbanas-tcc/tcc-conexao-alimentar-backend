package tcc.conexao_alimentar.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tcc.conexao_alimentar.enums.TipoUsuario;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaFisicaResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String documentoComprovante;
    private EnderecoDTO endereco;
    private TipoUsuario tipoUsuario;
    private String fotoUrl;
}
