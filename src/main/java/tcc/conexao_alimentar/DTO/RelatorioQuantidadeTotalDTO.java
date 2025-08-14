package tcc.conexao_alimentar.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioQuantidadeTotalDTO {
    private String categoria;
    private Double quantidadeTotal;
    private String unidadeMedida;

}
