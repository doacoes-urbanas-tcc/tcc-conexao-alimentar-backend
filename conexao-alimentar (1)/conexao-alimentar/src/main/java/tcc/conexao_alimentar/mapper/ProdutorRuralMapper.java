package tcc.conexao_alimentar.mapper;

import tcc.conexao_alimentar.DTO.ProdutorRuralCadastroDTO;
import tcc.conexao_alimentar.DTO.ProdutorRuralResponseDTO;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.model.ProdutorRuralModel;

public class ProdutorRuralMapper {

    public static ProdutorRuralModel toEntity(ProdutorRuralCadastroDTO dto) {
        ProdutorRuralModel p = new ProdutorRuralModel();
        p.setNome(dto.getNome());
        p.setEmail(dto.getEmail());
        p.setSenha(dto.getSenha());
        p.setTelefone(dto.getTelefone());
        p.setNumeroRegistroRural(dto.getNumeroRegistroRural());
        p.setEndereco(EnderecoMapper.toEntity(dto.getEndereco()));
        p.setTipoUsuario(TipoUsuario.PRODUTOR_RURAL);
        return p;
    }

    public static ProdutorRuralResponseDTO toResponse(ProdutorRuralModel p) {
        return new ProdutorRuralResponseDTO(
            p.getId(), p.getNome(), p.getEmail(), p.getTelefone(),
            p.getNumeroRegistroRural(), EnderecoMapper.toDTO(p.getEndereco()), p.getTipoUsuario()
        );
    }

}
