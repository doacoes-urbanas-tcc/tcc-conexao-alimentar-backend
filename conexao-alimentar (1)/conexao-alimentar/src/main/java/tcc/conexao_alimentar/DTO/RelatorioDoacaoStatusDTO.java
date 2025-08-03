package tcc.conexao_alimentar.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcc.conexao_alimentar.enums.StatusDoacao;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioDoacaoStatusDTO {

    private Long idDoacao;
    private String nomeAlimento;
    private String categoria;
    private StatusDoacao status;
    private LocalDate dataExpiracao;
    private String nomeDoador;
    private String cnpjDoador;

}
