package tcc.conexao_alimentar.model;

import jakarta.persistence.*;
import lombok.*;
import tcc.conexao_alimentar.enums.CategoriaAlimento;
import tcc.conexao_alimentar.enums.Medida;
import tcc.conexao_alimentar.enums.StatusDoacao;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DoacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_alimento", nullable = false)
    private String nomeAlimento;

    private String descricao;

    private LocalDate dataCadastro;

    private LocalDate dataExpiracao;
    private String categoria;

    private Integer quantidade;

    private String localizacao;

    @Enumerated(EnumType.STRING)
    private StatusDoacao status;

    @ManyToOne
    @JoinColumn(name = "doador_id")
    private UsuarioModel doador;
    
}
