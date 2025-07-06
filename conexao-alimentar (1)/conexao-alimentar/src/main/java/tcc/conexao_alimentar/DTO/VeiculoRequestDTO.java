package tcc.conexao_alimentar.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoRequestDTO {
    private String placa;
    private String modelo;
    private String cor;
    private String capacidadeCarga;
    private Long voluntarioId;

}
