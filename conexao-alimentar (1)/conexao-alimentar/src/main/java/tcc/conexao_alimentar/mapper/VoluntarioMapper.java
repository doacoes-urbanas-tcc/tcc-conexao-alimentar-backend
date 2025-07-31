package tcc.conexao_alimentar.mapper;

import tcc.conexao_alimentar.DTO.VoluntarioRequestDTO;
import tcc.conexao_alimentar.DTO.VoluntarioResponseDTO;
import tcc.conexao_alimentar.enums.SetorAtuacao;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.model.VoluntarioModel;

public class VoluntarioMapper {

      public static VoluntarioModel toEntity(VoluntarioRequestDTO dto) {
        VoluntarioModel v = new VoluntarioModel();
        v.setNome(dto.getNome());
        v.setEmail(dto.getEmail());
        v.setSenha(dto.getSenha());
        v.setTelefone(dto.getTelefone());
        v.setCpf(dto.getCpf());
        if (dto.getSetorAtuacao() != null && !dto.getSetorAtuacao().isBlank()) {
            try {
                v.setSetorAtuacao(SetorAtuacao.valueOf(dto.getSetorAtuacao().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Setor de atuação inválido: " + dto.getSetorAtuacao());
            }
        } else {
            throw new IllegalArgumentException("Setor de atuação não pode ser nulo ou vazio");
        }
        v.setEndereco(EnderecoMapper.toEntity(dto.getEndereco()));
        v.setTipoUsuario(TipoUsuario.VOLUNTARIO);
        v.setFotoUrl(dto.getFotoUrl());
        return v;
    }

    public static VoluntarioResponseDTO toResponse(VoluntarioModel v) {
        return new VoluntarioResponseDTO(
            v.getId(), 
            v.getNome(), 
            v.getEmail(), 
            v.getTelefone(),
            v.getCpf(), 
            v.getDocumentoComprovante(),
            v.getSetorAtuacao() != null ? v.getSetorAtuacao().name() : null,
            EnderecoMapper.toDTO(v.getEndereco()), 
            v.getTipoUsuario(),
            v.getFotoUrl(),
            v.getUrlCnh(),
            v.getJustificativaReprovacao()
        );
    }

}
