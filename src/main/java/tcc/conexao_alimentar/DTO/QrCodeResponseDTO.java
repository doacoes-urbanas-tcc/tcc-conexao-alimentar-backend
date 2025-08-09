package tcc.conexao_alimentar.DTO;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrCodeResponseDTO {
    private String url;
    private long segundosRestantes;
    private Long reservaId;
    private OffsetDateTime dataReserva;
    private String statusReserva;
    
}
