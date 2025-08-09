package tcc.conexao_alimentar.DTO;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcc.conexao_alimentar.enums.StatusReserva;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaResponseDTO {

    private Long id;
    private Long doacaoId;
    private Long beneficiarioId;
    private OffsetDateTime dataReserva;
    private StatusReserva status;
    private String justificativaCancelamento;
    private String urlQrCode;
    private String nomeAlimento;
    private String descricao;
    private String categoria;
    private String unidadeMedida;
    private Double quantidade;
    private LocalDate dataValidade;
    private String doadorNome;
    private String urlImagem;
    private String doadorTipo;
    private Long doadorId;
    private Boolean avaliacaoFeitaPeloDoador;
    private Boolean avaliacaoFeitaPeloReceptor;
    private Boolean necessitaAvaliacao;
    private OffsetDateTime dataExpiracao;





}
