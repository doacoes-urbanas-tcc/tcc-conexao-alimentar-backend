package tcc.conexao_alimentar.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(
        regexp = "^\\d{5}\\-\\d{3}$",
        message = "CEP inválido. Use o formato XXXXX-XXX"
    )
    private String cep;
    
    @NotBlank(message = "Logradouro é obrigatório")
    @Size(min = 3, max = 100, message = "Logradouro deve ter entre 3 e 100 caracteres")
    private String logradouro;
    @NotBlank(message = "Número é obrigatório")
    @Size(max = 10, message = "Número deve ter no máximo 10 caracteres")
    private String numero;
    @NotBlank(message = "Bairro é obrigatório")
    @Size(min = 3, max = 50, message = "Bairro deve ter entre 3 e 50 caracteres")
    private String bairro;
    @NotBlank(message = "Cidade é obrigatória")
    @Size(min = 3, max = 50, message = "Cidade deve ter entre 3 e 50 caracteres")
    private String cidade;
    @NotBlank(message = "Estado é obrigatório")
    @Pattern(
        regexp = "^[A-Z]{2}$",
        message = "Estado deve ser a sigla de 2 letras (UF), por exemplo: SP"
    )
    private String estado;
    
    private Double latitude;
    private Double longitude;

}
