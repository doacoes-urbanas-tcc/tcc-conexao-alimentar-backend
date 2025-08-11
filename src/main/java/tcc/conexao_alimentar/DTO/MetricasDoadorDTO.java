package tcc.conexao_alimentar.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricasDoadorDTO {
    private String nome;
    private long totalDoacoes;
    private long ongsBeneficiadas;
    private double mediaAvaliacoes;

}
