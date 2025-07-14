package tcc.conexao_alimentar.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoResponseDTO {
    private Long id;
    private String placa;
    private String modelo;
    private String cor;
    private String capacidadeCarga;

}
