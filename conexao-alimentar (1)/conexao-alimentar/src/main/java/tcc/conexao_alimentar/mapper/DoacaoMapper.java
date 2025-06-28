package tcc.conexao_alimentar.mapper;

import tcc.conexao_alimentar.DTO.DoacaoRequestDTO;
import tcc.conexao_alimentar.DTO.DoacaoResponseDTO;
import tcc.conexao_alimentar.model.DoacaoModel;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.enums.CategoriaAlimento;
import tcc.conexao_alimentar.enums.StatusDoacao;

import java.time.LocalDate;
public class DoacaoMapper {

    public static DoacaoModel toEntity(DoacaoRequestDTO dto, UsuarioModel doador) {
        DoacaoModel model = new DoacaoModel();
        model.setDescricao(dto.getDescricao());
        model.setDataCadastro(LocalDate.now());
        model.setDataExpiracao(dto.getDataExpiracao());
        model.setCategoria(CategoriaAlimento.valueOf(dto.getCategoria().toUpperCase()));
        model.setQuantidade(dto.getQuantidade());
        model.setLocalizacao(dto.getLocalizacao());
        model.setStatus(StatusDoacao.PENDENTE);
        model.setDoador(doador);
        return model;
    }

    public static DoacaoResponseDTO toResponse(DoacaoModel model) {
        return new DoacaoResponseDTO(
            model.getId(),
            model.getDescricao(),
            model.getDataCadastro(),
            model.getDataExpiracao(),
            model.getCategoria().name(),
            model.getQuantidade(),
            model.getLocalizacao(),
            model.getStatus(),
            model.getDoador().getNome(),
            model.getDoador().getId()
        );
    }

}
