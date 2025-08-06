package tcc.conexao_alimentar.DTO;

import java.time.LocalDateTime;

import tcc.conexao_alimentar.enums.StatusResposta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespostaTaskTiResponseDTO {

    private Long id;
    private Long voluntarioId;
    private String nomeVoluntario; 
    private String linkSolucao;
    private LocalDateTime dataResposta;
    private StatusResposta status;

}
