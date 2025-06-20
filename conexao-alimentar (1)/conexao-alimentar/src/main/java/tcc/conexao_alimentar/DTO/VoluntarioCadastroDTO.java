package tcc.conexao_alimentar.DTO;


import jakarta.persistence.Entity;
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
public class VoluntarioCadastroDTO {

    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private EnderecoDTO endereco;
    private String cpf;
    private String documentoComprovante;
    private SetorAtuacao setorAtuacao;

}
