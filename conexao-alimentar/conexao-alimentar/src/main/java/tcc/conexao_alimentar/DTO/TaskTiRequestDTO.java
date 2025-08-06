package tcc.conexao_alimentar.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskTiRequestDTO {

    private String titulo;
    private String descricao;
    private String linkRepositorio;
    private List<String> tags;

}
