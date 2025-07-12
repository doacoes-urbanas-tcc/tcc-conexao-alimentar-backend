package tcc.conexao_alimentar.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int nota;  

    private String comentario;

    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "avaliador_id")
    private UsuarioModel avaliador;

    @ManyToOne
    @JoinColumn(name = "avaliado_id")
    private UsuarioModel avaliado;

}
