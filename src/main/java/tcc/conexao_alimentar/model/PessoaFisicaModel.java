package tcc.conexao_alimentar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pessoa_fisica")
public class PessoaFisicaModel extends UsuarioModel{
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;
    @Column(name = "documento_comprovante", nullable = false)
    private String documentoComprovante;

    

}
