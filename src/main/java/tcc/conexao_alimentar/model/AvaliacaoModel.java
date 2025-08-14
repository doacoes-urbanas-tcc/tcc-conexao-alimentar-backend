package tcc.conexao_alimentar.model;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "avaliacao")
@Builder
public class AvaliacaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int nota;

    private String comentario;
    @Column(name = "data_criacao", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "avaliador_id", nullable = false)
    private UsuarioModel avaliador;

    @ManyToOne
    @JoinColumn(name = "avaliado_id", nullable = false)
    private UsuarioModel avaliado;

}
