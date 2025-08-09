package tcc.conexao_alimentar.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import tcc.conexao_alimentar.enums.StatusDoacao;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class DoacaoResponseDTO {

    private Long id;
    private String nomeAlimento;
    private String unidadeMedida; 
    private Double quantidade;
    private LocalDate dataValidade; 
    private String descricao;
    private OffsetDateTime dataCadastro;
    private OffsetDateTime dataExpiracao;
    private String categoria; 
    private StatusDoacao status;
    private String doadorNome;
    private Long doadorId;
    private String urlImagem;
    private Long idReserva;
    private String nomeReceptor;
    private Long idReceptor;
    private String tipoReceptor;
    private OffsetDateTime DataConclusao;

}
