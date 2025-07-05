package tcc.conexao_alimentar.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcc.conexao_alimentar.enums.SetorAtuacao;
import tcc.conexao_alimentar.enums.TipoUsuario;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoluntarioRequestDTO {

    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private EnderecoDTO endereco;
    private String cpf;
    private String documentoComprovante;
    private SetorAtuacao setorAtuacao;
    private TipoUsuario tipoUsuario;


}
