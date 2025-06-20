package tcc.conexao_alimentar.DTO;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tcc.conexao_alimentar.enums.TipoUsuario;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProdutorResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String numeroRegistroRural;
    private EnderecoDTO endereco;
    private TipoUsuario tipoUsuario;
}
