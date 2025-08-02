package tcc.conexao_alimentar.mapper;

import tcc.conexao_alimentar.DTO.ProdutorRuralRequestDTO;
import tcc.conexao_alimentar.DTO.ProdutorRuralResponseDTO;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.model.ProdutorRuralModel;

public class ProdutorRuralMapper {

    public static ProdutorRuralModel toEntity(ProdutorRuralRequestDTO dto) {
        ProdutorRuralModel p = new ProdutorRuralModel();
        p.setNome(dto.getNome());
        p.setEmail(dto.getEmail());
        p.setSenha(dto.getSenha());
        p.setTelefone(dto.getTelefone());
        p.setNumeroRegistroRural(dto.getNumeroRegistroRural());
        p.setEndereco(EnderecoMapper.toEntity(dto.getEndereco()));
        p.setTipoUsuario(TipoUsuario.PRODUTOR_RURAL);
        p.setFotoUrl(dto.getFotoUrl());
        return p;
    }

    public static ProdutorRuralResponseDTO toResponse(ProdutorRuralModel p) {
        return new ProdutorRuralResponseDTO(
            p.getId(), 
            p.getNome(), 
            p.getEmail(), 
            p.getTelefone(),
            p.getNumeroRegistroRural(), EnderecoMapper.toDTO(p.getEndereco()), 
            p.getTipoUsuario(),
            p.getFotoUrl(),
            p.getJustificativaReprovacao(),
            p.isAtivo()
        );
    }

}
