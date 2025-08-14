package tcc.conexao_alimentar.model;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tcc.conexao_alimentar.enums.StatusResposta;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "resposta_task")

public class RespostaTaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private VoluntarioModel voluntario;

    @ManyToOne
    private TaskTiModel taskTi;

    private String linkSolucao; 
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime dataResposta;

    @Enumerated(EnumType.STRING)
    private StatusResposta status = StatusResposta.PENDENTE;

}
