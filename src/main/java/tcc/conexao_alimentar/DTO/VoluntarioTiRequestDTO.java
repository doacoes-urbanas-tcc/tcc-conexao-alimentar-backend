package tcc.conexao_alimentar.DTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcc.conexao_alimentar.enums.SetorTi;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoluntarioTiRequestDTO {
    @Enumerated(EnumType.STRING)
    private SetorTi setorTi;
    private String stackConhecimento;
    private String certificacoes;
    private String experiencia;
    private String linkedin;
    private String github;
    private Double disponibilidadeHoras;
        

}
