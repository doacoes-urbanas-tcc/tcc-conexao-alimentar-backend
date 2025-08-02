package tcc.conexao_alimentar.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResumoDTO {
    private long usuariosAtivos;
    private long usuariosPendentes;
    private long usuariosInativos;
    private long doacoesAtivas;
    private long tasksAbertas;

}
