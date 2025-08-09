package tcc.conexao_alimentar.model;

import jakarta.persistence.*;
import lombok.*;
import tcc.conexao_alimentar.enums.CategoriaAlimento;
import tcc.conexao_alimentar.enums.Medida;
import tcc.conexao_alimentar.enums.StatusDoacao;

import java.time.LocalDate;
import java.time.OffsetDateTime;

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
    @Column(name = "data_cadastro", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime dataCadastro;
    @Column(name = "data_expiracao", nullable = false,columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime dataExpiracao;
    @Column(name = "url_imagem")
    private String urlImagem;
    @Enumerated(EnumType.STRING)
    private StatusDoacao status;
    @ManyToOne
    @JoinColumn(name = "doador_id")
    private UsuarioModel doador;
    @OneToOne(mappedBy = "doacao", cascade = CascadeType.ALL)
    private ReservaModel reserva;
    @Column(name = "data_conclusao", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime dataConclusao;

    
}
