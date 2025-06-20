package tcc.conexao_alimentar.model;


import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcc.conexao_alimentar.enums.TipoUsuario;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;
    private String telefone;

    @Embedded
    private EnderecoModel endereco;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    private Boolean ativo;

}
