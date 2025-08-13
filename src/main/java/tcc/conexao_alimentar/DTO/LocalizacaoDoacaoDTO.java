package tcc.conexao_alimentar.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalizacaoDoacaoDTO {
    private Long idDoacao;
    private String nomeAlimento;
    private String doadorNome;
    private EnderecoDTO endereco;

}
