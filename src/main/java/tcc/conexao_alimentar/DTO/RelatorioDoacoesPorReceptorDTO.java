package tcc.conexao_alimentar.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioDoacoesPorReceptorDTO {
    
    private String nomeReceptor;
    private String cnpjReceptor;
    private String categoria;
    private double quantidadeTotal;
    private String unidade;


}
