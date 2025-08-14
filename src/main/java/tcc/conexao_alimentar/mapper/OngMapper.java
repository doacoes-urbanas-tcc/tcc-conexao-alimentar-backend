package tcc.conexao_alimentar.mapper;

import tcc.conexao_alimentar.DTO.OngRequestDTO;
import tcc.conexao_alimentar.DTO.OngResponseDTO;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.model.OngModel;

public class OngMapper {

    public static OngModel toEntity(OngRequestDTO dto) {
        OngModel o = new OngModel();
        o.setNome(dto.getNome());
        o.setEmail(dto.getEmail());
        o.setSenha(dto.getSenha());
        o.setTelefone(dto.getTelefone());
        o.setCnpj(dto.getCnpj());
        o.setDescricao(dto.getDescricao());
        o.setEndereco(EnderecoMapper.toEntity(dto.getEndereco()));
        o.setTipoUsuario(TipoUsuario.ONG);
        o.setFotoUrl(dto.getFotoUrl());
        return o;
    }

    public static OngResponseDTO toResponse(OngModel o) {
        return new OngResponseDTO(
            o.getId(), 
            o.getNome(), 
            o.getEmail(), 
            o.getTelefone(),
            o.getCnpj(), 
            o.getDescricao(), 
            EnderecoMapper.toDTO(o.getEndereco()), 
            o.getTipoUsuario(),
            o.getFotoUrl(),
            o.getJustificativaReprovacao(),
            o.getStatus()
        );
    }

    

}
