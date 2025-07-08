package tcc.conexao_alimentar.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservaRequestDTO {

    @NotNull(message = "ID da doação é obrigatório.")
    private Long doacaoId;
    @NotNull(message = "ID do beneficiário é obrigatório.")
    private Long beneficiarioId;
}
