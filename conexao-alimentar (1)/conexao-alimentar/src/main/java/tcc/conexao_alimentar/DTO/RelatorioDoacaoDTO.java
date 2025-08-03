package tcc.conexao_alimentar.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioDoacaoDTO {

    private Long idDoacao;
    private String nomeAlimento;
    private String unidadeMedida;
    private Double quantidade;
    private LocalDate dataConclusao;
    private String categoria;

    private String nomeDoador;
    private String cnpjDoador;

    private String nomeOng;
    private String cnpjOng;

}
