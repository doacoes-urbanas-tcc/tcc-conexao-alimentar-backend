package tcc.conexao_alimentar.mapper;

import java.time.LocalDateTime;

import tcc.conexao_alimentar.DTO.ReservaRequestDTO;
import tcc.conexao_alimentar.DTO.ReservaResponseDTO;
import tcc.conexao_alimentar.enums.StatusReserva;
import tcc.conexao_alimentar.model.DoacaoModel;
import tcc.conexao_alimentar.model.ReservaModel;
import tcc.conexao_alimentar.model.UsuarioModel;

public class ReservaMapper {

     public static ReservaResponseDTO toResponse(ReservaModel reserva) {
        DoacaoModel doacao = reserva.getDoacao();
        UsuarioModel doador = doacao.getDoador();

       return new ReservaResponseDTO(
        reserva.getId(),
        doacao.getId(),
        reserva.getReceptor().getId(),
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
        doador.getId() 
    );
    }

    public static ReservaModel toEntity(
            ReservaRequestDTO dto,
            DoacaoModel doacao,
            UsuarioModel receptor
    ) {
        ReservaModel model = new ReservaModel();
        model.setDoacao(doacao);
        model.setReceptor(receptor);
        model.setDataReserva(LocalDateTime.now());
        model.setStatus(StatusReserva.RESERVADA);
        model.setJustificativaCancelamento(null);
        return model;
    }

}
