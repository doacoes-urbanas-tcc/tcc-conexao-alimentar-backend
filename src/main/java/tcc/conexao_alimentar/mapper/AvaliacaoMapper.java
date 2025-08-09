package tcc.conexao_alimentar.mapper;

import java.time.OffsetDateTime;

import tcc.conexao_alimentar.DTO.AvaliacaoRequestDTO;
import tcc.conexao_alimentar.DTO.AvaliacaoResponseDTO;
import tcc.conexao_alimentar.model.AvaliacaoModel;
import tcc.conexao_alimentar.model.UsuarioModel;

public class AvaliacaoMapper {
    public static AvaliacaoModel toEntity(AvaliacaoRequestDTO dto, UsuarioModel avaliador, UsuarioModel avaliado) {
        AvaliacaoModel model = new AvaliacaoModel();
        model.setNota(dto.getNota());
        model.setComentario(dto.getComentario());
        model.setDataCriacao(OffsetDateTime.now());
        model.setAvaliador(avaliador);
        model.setAvaliado(avaliado);
        return model;
    }

    public static AvaliacaoResponseDTO toResponse(AvaliacaoModel model) {
        return AvaliacaoResponseDTO.builder()
            .id(model.getId())
            .nomeAvaliador(model.getAvaliador().getNome())
            .nomeAvaliado(model.getAvaliado().getNome())
            .nota(model.getNota())
            .comentario(model.getComentario())
            .dataCriacao(model.getDataCriacao())
            .fotoAvaliador(model.getAvaliador().getFotoUrl())
            .build();
    }

}
