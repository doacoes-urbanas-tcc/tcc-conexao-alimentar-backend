package tcc.conexao_alimentar.model;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tcc.conexao_alimentar.enums.SetorAtuacao;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "voluntario")
public class VoluntarioModel extends UsuarioModel{

    private String cpf;
    private String documentoComprovante;

    @Enumerated(EnumType.STRING)
    private SetorAtuacao setorAtuacao;

}
