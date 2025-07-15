package tcc.conexao_alimentar.DTO;

import lombok.Data;

@Data
public class QrCodeDTO {
    private Long doacaoId;
    private String nomeAlimento;
    private String unidadeMedida;
    private Double quantidade;
    private String dataValidade;
    private String descricao;
    private String categoria;

}
