package tcc.conexao_alimentar.DTO;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardVoluntarioTIResponseDTO {
    private String nome;
    private int tasksRespondidas;
    private int tasksAbertas;
    private double mediaAvaliacoes;
    private List<RespostaDTO> respostas;

}
