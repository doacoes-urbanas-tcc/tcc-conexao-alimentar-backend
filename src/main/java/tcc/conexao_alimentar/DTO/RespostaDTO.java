package tcc.conexao_alimentar.DTO;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespostaDTO {
    private String tituloTask;
    private String linkSolucao;
    private LocalDateTime dataResposta;


}
