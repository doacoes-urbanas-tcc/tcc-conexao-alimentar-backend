package tcc.conexao_alimentar.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioEfetividadeDTO {

    private String categoria;
    private long totalDoacoes;
    private long concluidas;
    private double efetividadePercentual;

}
