package tcc.conexao_alimentar.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import tcc.conexao_alimentar.enums.StatusDoacao;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DoacaoResponseDTO {

    private String nomeAlimento;
    private String unidadeMedida; 
    private String descricao;
    private LocalDate dataCadastro;
    private LocalDate dataExpiracao;
    private String categoria; 
    private Double quantidade;
    private String localizacao;
    private StatusDoacao status;
    private String doadorNome;
    private Long doadorId;

}
