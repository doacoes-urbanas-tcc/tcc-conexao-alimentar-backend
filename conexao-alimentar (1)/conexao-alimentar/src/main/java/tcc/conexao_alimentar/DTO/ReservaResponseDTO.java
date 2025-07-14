package tcc.conexao_alimentar.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import tcc.conexao_alimentar.enums.StatusReserva;
@Data
@AllArgsConstructor
public class ReservaResponseDTO {

    private Long id;
    private Long doacaoId;
    private Long beneficiarioId;
    private LocalDateTime dataReserva;
    private StatusReserva status;
    private String justificativaCancelamento;

}
