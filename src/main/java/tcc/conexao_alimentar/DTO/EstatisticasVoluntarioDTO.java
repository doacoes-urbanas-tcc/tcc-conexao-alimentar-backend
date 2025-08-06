package main.java.tcc.conexao_alimentar.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstatisticasVoluntarioDTO {
    private String nome;
    private int tasksRespondidas;
    private int tasksAbertas;
    private double mediaAvaliacoes;

}
