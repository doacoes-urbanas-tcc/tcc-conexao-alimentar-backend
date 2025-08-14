package tcc.conexao_alimentar.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioDoacoesPorDoadorDTO {
    private String nomeDoador;
    private String cnpjDoador;
    private String categoria;
    private double quantidadeTotal;
    private String unidade;

}
