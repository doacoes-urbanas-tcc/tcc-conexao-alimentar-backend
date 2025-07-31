package tcc.conexao_alimentar.mapper;

import tcc.conexao_alimentar.DTO.ComercioRequestDTO;
import tcc.conexao_alimentar.DTO.ComercioResponseDTO;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.model.ComercioModel;

public class ComercioMapper {

     public static ComercioModel toEntity(ComercioRequestDTO dto) {
        ComercioModel c = new ComercioModel();
        c.setNome(dto.getNome());
        c.setEmail(dto.getEmail());
        c.setSenha(dto.getSenha());
        c.setTelefone(dto.getTelefone());
        c.setCnpj(dto.getCnpj());
        c.setNomeFantasia(dto.getNomeFantasia());
        c.setTipoComercio(dto.getTipoComercio());
        c.setEndereco(EnderecoMapper.toEntity(dto.getEndereco()));
        c.setTipoUsuario(TipoUsuario.COMERCIO);
        c.setFotoUrl(dto.getFotoUrl());
        return c;
    }

    public static ComercioResponseDTO toResponse(ComercioModel c) {
        return new ComercioResponseDTO(
            c.getId(), 
            c.getNome(),
            c.getEmail(), 
            c.getTelefone(),
            c.getCnpj(), 
            c.getNomeFantasia(), 
            c.getTipoComercio(),
            EnderecoMapper.toDTO(c.getEndereco()), 
            c.getTipoUsuario(),
            c.getFotoUrl(),
            c.getJustificativaReprovacao()
        );
    }

}
