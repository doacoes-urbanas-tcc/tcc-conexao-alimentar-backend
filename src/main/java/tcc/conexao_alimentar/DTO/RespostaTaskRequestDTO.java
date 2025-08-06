package tcc.conexao_alimentar.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespostaTaskRequestDTO {

    private Long taskId;
    private Long voluntarioId;
    private String linkSolucao;

}
