package tcc.conexao_alimentar.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardVoluntarioTIResponseDTO {
    private long quantidadeTasksRespondidas;
    private long quantidadeTasksAbertasCompativeis;
    private double mediaAvaliacoesRecebidas;

}
