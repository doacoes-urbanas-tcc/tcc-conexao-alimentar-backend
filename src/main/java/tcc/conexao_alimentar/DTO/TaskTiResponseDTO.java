package tcc.conexao_alimentar.DTO;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskTiResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String linkRepositorio;
    private List<String> tags;
    private boolean fechada;
    private OffsetDateTime dataCriacao;

}
