package tcc.conexao_alimentar.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoacaoRequestDTO {

    @NotBlank(message = "Nome do alimento é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    private String nomeAlimento;
    @NotBlank(message = "Unidade de medida é obrigatória")
    private String unidadeMedida;
    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser maior que zero")
    private Double quantidade;
    @NotNull(message = "Data de validade é obrigatória")
    @Future(message = "Data de validade deve ser no futuro")
    private LocalDate dataValidade;
    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 255, message = "Descrição não pode passar de 255 caracteres")
    private String descricao;
    @NotBlank(message = "Categoria é obrigatória")
    @Pattern(regexp = "^(HORTIFRUTI|PADARIA|GRAOS|CARNES|LATICINIOS|BEBIDAS|CONSERVAS|MASSAS|DOCES|OUTROS)$", message = "Categoria deve ser válida")
    private String categoria;
    private String urlImagem;
 

}
