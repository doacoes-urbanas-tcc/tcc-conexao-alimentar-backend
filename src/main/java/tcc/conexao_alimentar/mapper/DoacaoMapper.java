package tcc.conexao_alimentar.mapper;

import tcc.conexao_alimentar.DTO.DoacaoRequestDTO;
import tcc.conexao_alimentar.DTO.DoacaoResponseDTO;
import tcc.conexao_alimentar.model.DoacaoModel;
import tcc.conexao_alimentar.model.ReservaModel;
import tcc.conexao_alimentar.model.UsuarioModel;
import tcc.conexao_alimentar.enums.CategoriaAlimento;
import tcc.conexao_alimentar.enums.Medida;
import tcc.conexao_alimentar.enums.StatusDoacao;

import java.time.OffsetDateTime;
public class DoacaoMapper {


     public static DoacaoModel toEntity(DoacaoRequestDTO dto, UsuarioModel doador) {
        DoacaoModel model = new DoacaoModel();
        model.setNomeAlimento(dto.getNomeAlimento());
        if (dto.getUnidadeMedida() != null) {
            model.setUnidadeMedida(Medida.valueOf(dto.getUnidadeMedida().toUpperCase()));
        }
        model.setQuantidade(dto.getQuantidade());
        model.setDataValidade(dto.getDataValidade());
        model.setDescricao(dto.getDescricao());
        if (dto.getCategoria() != null) {
            model.setCategoria(CategoriaAlimento.valueOf(dto.getCategoria().toUpperCase()));
        }
        model.setDataCadastro(OffsetDateTime.now());
        model.setStatus(StatusDoacao.PENDENTE);
        model.setDoador(doador);
        model.setUrlImagem(dto.getUrlImagem());
        return model;
    }

    public static DoacaoResponseDTO toResponse(DoacaoModel doacao) {
        ReservaModel reserva = doacao.getReserva();

        Long idReserva = null;
        String nomeReceptor = null;
        Long idReceptor = null;
        String tipoReceptor = null;

        if (reserva != null && reserva.getReceptor() != null) {
            idReserva = reserva.getId();
            nomeReceptor = reserva.getReceptor().getNome();
            idReceptor = reserva.getReceptor().getId();
            tipoReceptor = reserva.getReceptor().getTipoUsuario().toString();
        }

        Double doadorLat = null;
        Double doadorLng = null;
        if (doacao.getDoador() != null && doacao.getDoador().getEndereco() != null) {
            doadorLat = doacao.getDoador().getEndereco().getLatitude();
            doadorLng = doacao.getDoador().getEndereco().getLongitude();
        }

        return new DoacaoResponseDTO(
            doacao.getId(),
            doacao.getNomeAlimento(),
            doacao.getUnidadeMedida() != null ? doacao.getUnidadeMedida().toString() : null,
            doacao.getQuantidade(),
            doacao.getDataValidade(),
            doacao.getDescricao(),
            doacao.getDataCadastro(),
            doacao.getDataExpiracao(),
            doacao.getCategoria() != null ? doacao.getCategoria().toString() : null,
            doacao.getStatus(),
            doacao.getDoador() != null ? doacao.getDoador().getNome() : null,
            doacao.getDoador() != null ? doacao.getDoador().getId() : null,
            doacao.getUrlImagem(),
            idReserva,
            nomeReceptor,
            idReceptor,
            tipoReceptor,
            doacao.getDataConclusao() != null ? doacao.getDataConclusao() : null,
            doadorLat,
            doadorLng
        );
    }
}



