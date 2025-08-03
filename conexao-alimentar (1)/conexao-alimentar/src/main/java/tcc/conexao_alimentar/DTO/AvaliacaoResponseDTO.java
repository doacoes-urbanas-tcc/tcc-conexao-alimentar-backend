package tcc.conexao_alimentar.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvaliacaoResponseDTO {
    private Long id;
    private String nomeAvaliador;
    private String nomeAvaliado;
    private int nota;
    private String comentario;
    private LocalDateTime dataCriacao;
    private String fotoAvaliador;


}
