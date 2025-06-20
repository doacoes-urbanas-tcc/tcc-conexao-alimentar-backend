package tcc.conexao_alimentar.DTO;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tcc.conexao_alimentar.enums.TipoComercio;
import tcc.conexao_alimentar.enums.TipoUsuario;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ComercioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cnpj;
    private String nomeFantasia;
    private TipoComercio tipoComercio;
    private EnderecoDTO endereco;
    private TipoUsuario tipoUsuario;

}
