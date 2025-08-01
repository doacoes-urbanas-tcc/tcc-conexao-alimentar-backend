package tcc.conexao_alimentar.model;

import jakarta.persistence.*;
import lombok.*;
import tcc.conexao_alimentar.enums.CategoriaAlimento;
import tcc.conexao_alimentar.enums.Medida;
import tcc.conexao_alimentar.enums.StatusDoacao;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doacao")
public class DoacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_alimento", nullable = false)
    private String nomeAlimento;
    @Column(name = "unidade_medida", nullable = false)
    private Medida unidadeMedida;
    @Column(name = "quantidade", nullable = false)
    private Double quantidade;
    @Column(name = "data_validade", nullable = false)
    private LocalDate dataValidade;
    @Column(name = "descricao", nullable = false)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaAlimento categoria;
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    @Column(name = "data_expiracao", nullable = false)
    private LocalDateTime dataExpiracao;
    @Column(name = "url_imagem")
    private String urlImagem;
    @Enumerated(EnumType.STRING)
    private StatusDoacao status;
    @ManyToOne
    @JoinColumn(name = "doador_id")
    private UsuarioModel doador;
    @OneToOne(mappedBy = "doacao", cascade = CascadeType.ALL)
    private ReservaModel reserva;

    
}
