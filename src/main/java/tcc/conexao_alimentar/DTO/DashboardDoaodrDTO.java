package main.java.tcc.conexao_alimentar.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDoaodrDTO {
    private int totalDoacoes;
    private int ongsBeneficiadas;
    private Double mediaAvaliacoes;
    private UltimaDoacaoDTO ultimaDoacao;

}
