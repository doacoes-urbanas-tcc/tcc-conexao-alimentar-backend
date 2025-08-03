package tcc.conexao_alimentar.DTO;

import java.time.LocalDateTime;

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
    private LocalDateTime dataReserva;
    private String statusReserva;
    
}
