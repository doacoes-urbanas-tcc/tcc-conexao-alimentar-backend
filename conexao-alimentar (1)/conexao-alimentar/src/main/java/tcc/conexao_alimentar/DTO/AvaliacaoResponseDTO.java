package tcc.conexao_alimentar.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoResponseDTO {
    private Long id;
    private int nota;
    private String comentario;
    private String avaliador;   
    private String avaliado;    
    private LocalDateTime dataCriacao;

}
