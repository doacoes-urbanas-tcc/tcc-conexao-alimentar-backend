package tcc.conexao_alimentar.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoRequestDTO {
    
    @NotBlank(message = "Placa é obrigatória.")
    @Pattern(
        regexp = "^[A-Z]{3}-\\d{4}$|^[A-Z]{3}\\d[A-Z]\\d{2}$",
        message = "Placa deve estar no formato válido (ex: ABC-1234 ou ABC1D23)."
    )
    private String placa;
    @NotBlank(message = "Modelo é obrigatório.")
    private String modelo;
    @NotBlank(message = "Modelo é obrigatório.")
    private String cor;
    @NotBlank(message = "Capacidade de carga é obrigatória.")
    @Pattern(
        regexp = "^[0-9]+(kg|KG|Kg|ton|TON|Ton)$",
        message = "Capacidade deve incluir unidade (ex: 500kg ou 1ton)."
    )
    private String capacidadeCarga;
    @NotNull(message = "ID do voluntário é obrigatório.")
    private Long voluntarioId;
    private String urlCnh; 

}
