package tcc.conexao_alimentar.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoRequestDTO {
    @Min(1)
    @Max(5)
    private int nota;

    @NotBlank
    private String comentario;

}
