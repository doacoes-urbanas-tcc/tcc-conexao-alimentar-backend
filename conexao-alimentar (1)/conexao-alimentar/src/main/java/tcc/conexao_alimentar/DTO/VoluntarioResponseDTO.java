package tcc.conexao_alimentar.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import tcc.conexao_alimentar.enums.SetorAtuacao;
import tcc.conexao_alimentar.enums.TipoUsuario;

@Data
@NoArgsConstructor
public class VoluntarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String documentoComprovante;
    private SetorAtuacao setorAtuacao;
    private EnderecoDTO endereco;
    private TipoUsuario tipoUsuario;
    private String urlCnh;
    private String fotoUrl;

    public VoluntarioResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        String documentoComprovante,
        SetorAtuacao setorAtuacao,
        EnderecoDTO endereco,
        TipoUsuario tipoUsuario,
        String fotoUrl
    ) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.documentoComprovante = documentoComprovante;
        this.setorAtuacao = setorAtuacao;
        this.endereco = endereco;
        this.tipoUsuario = tipoUsuario;
        this.fotoUrl = fotoUrl;
    }
}



