package tcc.conexao_alimentar.mapper;

import java.time.OffsetDateTime;

import tcc.conexao_alimentar.DTO.ReservaRequestDTO;
import tcc.conexao_alimentar.DTO.ReservaResponseDTO;
import tcc.conexao_alimentar.enums.StatusDoacao;
import tcc.conexao_alimentar.enums.StatusReserva;
import tcc.conexao_alimentar.enums.TipoUsuario;
import tcc.conexao_alimentar.model.DoacaoModel;
import tcc.conexao_alimentar.model.ReservaModel;
import tcc.conexao_alimentar.model.UsuarioModel;

public class ReservaMapper {

    public static ReservaResponseDTO toResponse(ReservaModel reserva) {
        DoacaoModel doacao = reserva.getDoacao();
        UsuarioModel doador = doacao.getDoador();
        UsuarioModel receptor = reserva.getReceptor();

        boolean necessitaAvaliacao = false;
        if (doacao.getStatus() == StatusDoacao.CONCLUIDA) {
            if (receptor.getTipoUsuario() == TipoUsuario.ONG && Boolean.FALSE.equals(reserva.getAvaliacaoFeitaPeloReceptor())) {
                necessitaAvaliacao = true;
            } else if (doador.getTipoUsuario() == TipoUsuario.COMERCIO && Boolean.FALSE.equals(reserva.getAvaliacaoFeitaPeloDoador())) {
                necessitaAvaliacao = true;
            }
        }

        ReservaResponseDTO dto = new ReservaResponseDTO(
            reserva.getId(),
            doacao.getId(),
            receptor.getId(),
            reserva.getDataReserva(),
            reserva.getStatus(),
            reserva.getJustificativaCancelamento(),
            reserva.getUrlQrCode(),
            doacao.getNomeAlimento(),
            doacao.getDescricao(),
            doacao.getCategoria().toString(),
            doacao.getUnidadeMedida().toString(),
            doacao.getQuantidade(),
            doacao.getDataValidade(),
            doador.getNome(),
            doacao.getUrlImagem(),
            doador.getTipoUsuario().name(),
            doador.getId(),
            reserva.getAvaliacaoFeitaPeloDoador(),
            reserva.getAvaliacaoFeitaPeloReceptor(),
            necessitaAvaliacao,
            reserva.getDataHoraExpiracao()
        );

        return dto;
    }

    public static ReservaModel toEntity(
            ReservaRequestDTO dto,
            DoacaoModel doacao,
            UsuarioModel receptor
    ) {
        ReservaModel model = new ReservaModel();
        model.setDoacao(doacao);
        model.setReceptor(receptor);
        model.setDataReserva(OffsetDateTime.now());
        model.setStatus(StatusReserva.RESERVADA);
        model.setJustificativaCancelamento(null);
        model.setAvaliacaoFeitaPeloDoador(false);
        model.setAvaliacaoFeitaPeloReceptor(false);
        model.setDataHoraExpiracao(OffsetDateTime.now().plusHours(2)); 
        return model;
    }
}

