package tcc.conexao_alimentar.mapper;

import tcc.conexao_alimentar.DTO.DoacaoRequestDTO;
import tcc.conexao_alimentar.DTO.DoacaoResponseDTO;
import tcc.conexao_alimentar.model.DoacaoModel;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.enums.CategoriaAlimento;
import tcc.conexao_alimentar.enums.Medida;
import tcc.conexao_alimentar.enums.StatusDoacao;

import java.time.LocalDate;
public class DoacaoMapper {

    public static DoacaoModel toEntity(DoacaoRequestDTO dto, UsuarioModel doador) {
        DoacaoModel model = new DoacaoModel();
        model.setNomeAlimento(dto.getNomeAlimento());
        if (dto.getUnidadeMedida() != null) {
            model.setUnidadeMedida(Medida.valueOf(dto.getUnidadeMedida().toUpperCase()));

        }
        model.setUnidadeMedida(Medida.valueOf(dto.getUnidadeMedida().toUpperCase()));
        model.setQuantidade(dto.getQuantidade());
        model.setDescricao(dto.getDescricao());
        if (dto.getCategoria() != null) {
            model.setCategoria(CategoriaAlimento.valueOf(dto.getCategoria().toUpperCase()));
        }
        model.setDataCadastro(LocalDate.now());
        model.setDataExpiracao(dto.getDataExpiracao());
        model.setLocalizacao(dto.getLocalizacao());
        model.setStatus(StatusDoacao.PENDENTE);
        model.setDoador(doador);
        return model;
    }

    public static DoacaoResponseDTO toResponse(DoacaoModel model) {
        return new DoacaoResponseDTO(
            model.getNomeAlimento(),
            model.getUnidadeMedida() != null ? model.getUnidadeMedida().name() : null,
            model.getDescricao(),
            model.getDataCadastro(),
            model.getDataExpiracao(),
            model.getCategoria() != null ? model.getCategoria().name() : null,
            model.getQuantidade(),
            model.getLocalizacao(),
            model.getStatus(),
            model.getDoador() != null ? model.getDoador().getNome() : null,
            model.getDoador() != null ? model.getDoador().getId() : null
        
        );
    }

}
