package tcc.conexao_alimentar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    @Column(name = "setor_ti", nullable = false)
    private SetorTi setorTi;
    @Column(name = "stack_conhecimento", nullable = false)
    private String stackConhecimento;
    @Column(name = "certificacoes", nullable = false)
    private String certificacoes;
    @Column(name = "experiencia", nullable = false)
    private String experiencia;
    @Column(name = "linkedin", nullable = false)
    private String linkedin;
    @Column(name = "github", nullable = false)
    private String github;
    @Column(name = "disponibilidade_horas", nullable = false)
    private Double disponibilidadeHoras;
    @OneToOne
    @JoinColumn(name = "voluntario_id")
    private VoluntarioModel voluntario;
        


}
