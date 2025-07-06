package tcc.conexao_alimentar.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoacaoRequestDTO {

    private String nomeAlimento;
    private String unidadeMedida;
    private Double quantidade;
    private LocalDate dataValidade;
    private String descricao;
    private String categoria;

 

}
