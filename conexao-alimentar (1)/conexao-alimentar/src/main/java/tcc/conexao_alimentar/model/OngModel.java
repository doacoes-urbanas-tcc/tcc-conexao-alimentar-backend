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
@Table(name = "ong")
public class OngModel extends UsuarioModel {
    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;
    @Column(name = "descricao", nullable = false)
    private String descricao;

    

}
