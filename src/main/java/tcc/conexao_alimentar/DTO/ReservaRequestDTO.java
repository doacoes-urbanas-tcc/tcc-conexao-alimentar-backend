package tcc.conexao_alimentar.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservaRequestDTO {

    @NotNull(message = "ID da doação é obrigatório.")
    private Long doacaoId;
   
}
