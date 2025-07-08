package tcc.conexao_alimentar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tcc.conexao_alimentar.enums.SetorTi;

@Entity
@Getter
@Setter
@Table(name = "voluntario_ti")
public class VoluntarioTiModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private SetorTi setorTi;
    private String stackConhecimento;
    private String certificacoes;
    private String experiencia;
    private String linkedin;
    private String github;
    private Double disponibilidadeHoras;
        


}
