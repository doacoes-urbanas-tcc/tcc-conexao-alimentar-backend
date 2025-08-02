package tcc.conexao_alimentar.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcc.conexao_alimentar.enums.TipoUsuario;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProdutorRuralResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String numeroRegistroRural;
    private EnderecoDTO endereco;
    private TipoUsuario tipoUsuario;
    private String fotoUrl;
    private String justificativaReprovacao;
    private Boolean ativo;

}
