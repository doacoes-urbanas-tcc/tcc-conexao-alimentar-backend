package tcc.conexao_alimentar.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DoacaoRequestDTO {

    private String descricao;
    private LocalDate dataExpiracao;
    private String categoria;
    private Integer quantidade;
    private String localizacao;
    private Long doadorId;

 

}
