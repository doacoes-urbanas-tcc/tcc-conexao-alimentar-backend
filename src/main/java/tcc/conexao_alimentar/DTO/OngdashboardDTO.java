package tcc.conexao_alimentar.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OngdashboardDTO {
    private String nome;
    private Long totalDoacoesRecebidas;
    private Double mediaAvaliacoes;

}
