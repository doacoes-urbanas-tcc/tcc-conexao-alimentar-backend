package tcc.conexao_alimentar.mapper;

import tcc.conexao_alimentar.DTO.UsuarioResponseDTO;
import tcc.conexao_alimentar.model.UsuarioModel;

public class UsuarioMapper {
    
    public static UsuarioResponseDTO toResponse(UsuarioModel model) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(model.getId());
        dto.setNome(model.getNome());
        dto.setEmail(model.getEmail());
        dto.setTipoUsuario(model.getTipoUsuario());
        dto.setStatus(model.isAtivo() ? "ATIVO" : "PENDENTE");
        return dto;
    }

}
