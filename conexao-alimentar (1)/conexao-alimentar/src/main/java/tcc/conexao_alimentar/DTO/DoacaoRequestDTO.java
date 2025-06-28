package tcc.conexao_alimentar.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class DoacaoRequestDTO {

    private String nomeAlimento;
    private String unidadeMedida;
    private Double quantidade;
    private String descricao;
    private String categoria;
    private LocalDate dataExpiracao;
    private String localizacao;
    private Long doadorId;

 

}
